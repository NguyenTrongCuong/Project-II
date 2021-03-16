package root.api.client_close_room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import root.entity.Employee;
import root.entity.Room;
import root.entity_service.RoomService;
import root.utils.Notification;

@RestController
public class CloseRoomController {
	@Autowired
	private RoomService roomService;
	@Autowired
	private SimpMessagingTemplate template;
	
	@PutMapping("/close-room/{roomId}")
	public void closeRoom(@PathVariable("roomId") long roomId) {
		Room room = this.roomService.findRoomById(roomId).get();
		room.setClosed(true);
		this.roomService.updateRoom(room);
		this.sendClosingNotification(room);
	}
	
	private void sendClosingNotification(Room room) {
		Employee employee = room.getEmployee();
		String notificationReceiverEmail = employee.getEmail();
		Notification<Long, String> notification = new Notification<Long, String>(room.getId(), "Closing");
		this.template.convertAndSend("/queue/message-notification-of-" + notificationReceiverEmail, notification);
	}

}























































