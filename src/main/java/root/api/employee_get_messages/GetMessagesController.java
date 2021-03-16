package root.api.employee_get_messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import root.entity.Message;
import root.entity_service.MessageService;
import root.utils.MessageDTO;

@RestController
public class GetMessagesController {
	@Autowired
	private MessageService messageService;
	
	@GetMapping("/employee/get-messages/{roomId}")
	public List<MessageDTO> getMessages(@PathVariable("roomId") long roomId) {
		List<MessageDTO> messagesDTO = new ArrayList<>();
		Optional<List<Message>> result = this.messageService.findMessagesOfRoom(roomId);
		if(!result.isEmpty()) {
			List<Message> messages = result.get();
			for(Message message : messages) {
				messagesDTO.add(this.messageService.convertMessageToMessageDTO(message));
			}
		}
		return messagesDTO;
	}

}






































































