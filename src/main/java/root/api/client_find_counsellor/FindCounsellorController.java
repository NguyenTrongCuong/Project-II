package root.api.client_find_counsellor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import root.api.client_sign_in.ClientDetails;
import root.create_room_service.CreateRoomService;
import root.entity.Employee;
import root.entity.Room;
import root.entity_service.RoomService;
import root.utils.Notification;
import root.utils.RoomDTO;

@RestController
public class FindCounsellorController {
	@Autowired
	private CreateRoomService createRoomService;
	@Autowired
	private RoomService roomService;
	@Autowired
	private SimpMessagingTemplate template;
	
	@GetMapping("/find-counsellor")
	public void findCounsellor(@AuthenticationPrincipal ClientDetails clientDetails) {
		String clientEmail = clientDetails.getUsername();
		//get the new room
		Room room = this.createRoomService.createRoom(clientEmail);
		RoomDTO roomDTO = this.roomService.convertRoomToRoomDTO(room);
		Notification<RoomDTO, String> notification = new Notification<RoomDTO, String>(roomDTO, "Connecting");
		this.template.convertAndSend("/queue/message-notification-of-" + clientEmail, roomDTO);
		this.template.convertAndSend("/queue/message-notification-of-" + this.getEmailOfChosenEmployee(room), notification);
	}
	
	private String getEmailOfChosenEmployee(Room room) {
		Employee employee = room.getEmployee();
		return employee.getEmail();
	}

}




































