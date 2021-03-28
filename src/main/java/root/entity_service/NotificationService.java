package root.entity_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.entity_repository.NotificationRepository;

@Service
public class NotificationService {
	@Autowired
	private NotificationRepository notificationRepository;
	
	public void updateNotificationIsSeenOfANotification(Long id) {
		this.notificationRepository.updateNotificationIsSeenOfANotification(id);
	}

}
