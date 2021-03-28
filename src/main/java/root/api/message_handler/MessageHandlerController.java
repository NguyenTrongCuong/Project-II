package root.api.message_handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import root.api.client_sign_in.ClientDetails;
import root.api.employee_sign_in.EmployeeDetails;
import root.entity.Message;
import root.entity.MessageFromClientNotification;
import root.entity.Room;
import root.entity_service.MessageFromClientNotificationService;
import root.entity_service.MessageService;
import root.entity_service.RoomService;
import root.services.MessageFromClientNotificationDTOService;
import root.utils.MessageDTO;
import root.utils.MessageFromClientNotificationDTO;

@RestController
public class MessageHandlerController {
	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private MessageFromClientNotificationService messageFromClientNotificationService;
	
	@Autowired
	private MessageFromClientNotificationDTOService messageFromClientNotificationDTOService;

	
	@PostMapping(value={"/send-messages-to/{receiverEmail}/{roomId}", "/employee/send-messages-to/{receiverEmail}/{roomId}"})
	public void handleMessage(Message message,
							  @PathVariable("roomId") long roomId,
							  @PathVariable("receiverEmail") String receiverEmail,
							  @AuthenticationPrincipal UserDetails userDetails) {
		String senderEmail = userDetails.getUsername();
		String from = userDetails instanceof ClientDetails ? "client" : "employee";
		String senderFullName = "";
		String senderAvatarLink = "";
		
		List<Message> messages = this.messageService.handleMessage(message);
		
		Room room = this.roomService.findRoomWithMessagesLoadedEagerly(roomId).get();
		
		if(from.equals("client")) {
			ClientDetails clientDetails = (ClientDetails) userDetails;
			senderFullName = clientDetails.getCredentialsOfClient().getClient().getFullName();
			senderAvatarLink = clientDetails.getCredentialsOfClient().getClient().getAvatarLink();
		}
		else {
			EmployeeDetails employeeDetails = (EmployeeDetails) userDetails;
			senderFullName = employeeDetails.getEmployeeCredentials().getEmployee().getFullName();
			senderAvatarLink = employeeDetails.getEmployeeCredentials().getEmployee().getAvatarLink();
		}
		
		for(Message element : messages) {
			element.setSenderEmail(senderEmail);
			element.setSenderFullName(senderFullName);
			element.setSenderAvatarLink(senderAvatarLink);
			element.setRoom(room);
			room.getMessages().add(element);
		}
		
		messages = Lists.newArrayList(this.messageService.saveMessages(messages));
		this.roomService.updateRoom(room);
		
		List<MessageDTO> messageDTOs = new ArrayList<>();
		
		for(Message element : messages) {
			messageDTOs.add(this.messageService.convertMessageToMessageDTO(element));
		}
		
		this.template.convertAndSend("/topic/message-cover-of-" + roomId, messageDTOs);
		
		if(from.equals("client")) {
			MessageFromClientNotification notification = this.messageFromClientNotificationService.createNotification(room, "Message Notification");
			MessageFromClientNotificationDTO notificationDTO = this.messageFromClientNotificationDTOService.convertMessageFromClientNotificationToMessageFromClientNotificationDTO(notification);
			this.template.convertAndSend("/queue/notification-of-" + receiverEmail, notificationDTO);
		}
	}

}






















































































