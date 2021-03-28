package root.api.employee_find_unseen_message_notifications;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import root.entity.MessageFromClientNotification;
import root.entity_service.MessageFromClientNotificationService;
import root.services.MessageFromClientNotificationDTOService;
import root.utils.MessageFromClientNotificationDTO;

@RestController
public class FindUnseenMessageNotificationsController {
	@Autowired
	private MessageFromClientNotificationService messageFromClientNotificationService;
	
	@Autowired
	private MessageFromClientNotificationDTOService messageFromClientNotificationDTOService;
	
	@GetMapping("/employee/find-unseen-message-notifications-of-an-employee")
	public List<MessageFromClientNotificationDTO> findUnseenMessageNotifications(@RequestParam("employeeEmail") String employeeEmail) {
		Optional<List<MessageFromClientNotification>> result = this.messageFromClientNotificationService.findUnseenMessageNotificationsOfAnEmployee(employeeEmail);
		
		return this.messageFromClientNotificationDTOService.convertMessageFromClientNotificationsToMessageFromClientNotificationsDTO(result);
	}

}
















