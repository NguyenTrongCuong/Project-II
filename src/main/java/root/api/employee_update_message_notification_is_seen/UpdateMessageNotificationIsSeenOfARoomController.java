package root.api.employee_update_message_notification_is_seen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import root.entity_service.MessageFromClientNotificationService;

@RestController
public class UpdateMessageNotificationIsSeenOfARoomController {
	@Autowired
	private MessageFromClientNotificationService messageFromClientNotificationService;
	
	@PutMapping("/employee/update-message-notification-is-seen-of-a-room/{roomId}")
	public void updateMessageNotificationIsSeenOfARoom(@PathVariable("roomId") Long roomId) {
		this.messageFromClientNotificationService.updateMessageNotificationIsSeenOfARoom(roomId);
	}

}
