package root.api.update_message_notification_is_seen_of_a_notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import root.entity_service.NotificationService;

@RestController
public class UpdateMessageNotificationIsSeenOfANotification {
	@Autowired
	private NotificationService notificationService;
	
	@PutMapping("/employee/update-message-notification-is-seen-of-a-notification/{id}")
	public void updateMessageNotificationIsSeenOfANotification(@PathVariable("id") Long id) {
		this.notificationService.updateNotificationIsSeenOfANotification(id);
	}

}
