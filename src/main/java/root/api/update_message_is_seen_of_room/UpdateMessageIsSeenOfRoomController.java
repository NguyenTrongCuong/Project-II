package root.api.update_message_is_seen_of_room;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import root.api.employee_sign_in.EmployeeDetails;
import root.entity.Message;
import root.entity.Room;
import root.entity_service.MessageService;
import root.entity_service.RoomService;

@RestController
public class UpdateMessageIsSeenOfRoomController {
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private RoomService roomService;
	
	@PutMapping("/employee/update-message-is-seen-of-room/{roomId}")
	public void updateMessageIsSeenOfRoom(@PathVariable("roomId") long roomId, @AuthenticationPrincipal EmployeeDetails credentials) {
		String employeeEmail = credentials.getUsername();
		
		Room room = this.roomService.findRoomByIdWithMessagesLoadedEagerly(roomId).get();
		
		Set<Message> messages = this.roomService.findMessagesBySenderEmailOfRoom(room, employeeEmail);
		
		this.messageService.updateMessagesIsSeen(messages, 1);
		
	}
	

}










































