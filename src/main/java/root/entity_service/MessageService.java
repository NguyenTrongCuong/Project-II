package root.entity_service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import root.aws_s3_service.AWSS3Service;
import root.entity.Message;
import root.entity_repository.MessageRepository;
import root.extension_handler.ExtensionHandler;
import root.utils.MessageDTO;

@Service
public class MessageService {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private AWSS3Service awsS3Service;
	
	@Autowired
	@Qualifier("imageExtensionHandler")
	private ExtensionHandler imageExtensionHandler;
	
	@Autowired
	@Qualifier("documentExtensionHandler")
	private ExtensionHandler documentExtensionHandler;
	
	@Autowired
	@Qualifier("videoExtensionHandler")
	private ExtensionHandler videoExtensionHandler;
	
	public MessageDTO convertMessageToMessageDTO(Message message) {
		return new MessageDTO(message, clientService, employeeService);
	}
	
	public Optional<List<Message>> findMessagesOfRoom(long roomId) {
		return this.messageRepository.findMessagesOfRoom(roomId);
	}
	
	public Iterable<Message> saveMessages(List<Message> messages) {
		return this.messageRepository.saveAll(messages);
	}
	
	public List<Message> handleMessage(Message message) {
		List<Message> messages = new ArrayList<>();
		MultipartFile[] files = message.getFiles();
		
		if(files != null) {
			for(MultipartFile file : files) {
				String content = "";
				String fileName = file.getOriginalFilename();
				String fileLink = this.awsS3Service.uploadFile(file);
				String extension = this.getExtension(fileName);
				if(this.imageExtensionHandler.match(extension)) {
					content = "<img src='" + fileLink + "' class='image-message'/>";
				}
				else if(this.documentExtensionHandler.match(extension)) {
					content = "<a href='" + fileLink + "' download><i class='far fa-file icon-element'></i> " + fileName + "</a>";
				}
				else if(this.videoExtensionHandler.match(extension)) {
					content = "<video controls><source src='" + fileLink + "' type='video/mp4'></source></video>";
				}
				Message element = new Message();
				element.setBinary(true);
				element.setContent(content);
				element.setSentAt(Instant.now());
				messages.add(element);
			}
		}
		
		if(!message.getTextContent().equals("")) {
			Message element = new Message();
			element.setBinary(false);
			element.setContent(this.checkUrl(message.getTextContent()));
			element.setSentAt(Instant.now());
			messages.add(element);
		}
		
		return messages;
	}
	
	private String checkUrl(String message) {
		UrlValidator urlValidator = new UrlValidator();
		
		String[] elements = message.split(" ");
		
		Stream<String> stream = Arrays.stream(elements);
		
		return stream.map(element -> urlValidator.isValid(element) ? "<a href='" + element + "' target='_blank'>" + element + "</a>" : element)
				  	 .reduce("", (first, second) -> first + second + " ")
				  	 .trim();
	}
	
	private String getExtension(String fileName) {
		String[] levels = fileName.split("\\.");
		int lastIndex = levels.length - 1;
		return levels[lastIndex];
	}

}












































