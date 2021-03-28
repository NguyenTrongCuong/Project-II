package root.entity_service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import root.entity.MessageFromClientNotification;
import root.entity.Room;
import root.entity_repository.MessageFromClientNotificationRepository;

@Service
public class MessageFromClientNotificationService {
	@Autowired
	private MessageFromClientNotificationRepository messageFromClientNotificationRepository;
	
	public Optional<List<MessageFromClientNotification>> findUnseenMessageNotificationsOfAnEmployee(String employeeEmail) {
		return this.messageFromClientNotificationRepository.findUnseenMessageNotificationsOfAnEmployee(employeeEmail);
	}
	
	public MessageFromClientNotification saveMessageFromClientNotification(MessageFromClientNotification notification) {
		return this.messageFromClientNotificationRepository.save(notification);
	}
	
	public MessageFromClientNotification createNotification(Room room, String type) {
		MessageFromClientNotification notification = new MessageFromClientNotification();
		notification.setRoom(room);
		notification.setType(type);
		return this.messageFromClientNotificationRepository.save(notification);
	}
	
	public void updateMessageNotificationIsSeenOfARoom(Long roomId) {
		this.messageFromClientNotificationRepository.updateMessageNotificationIsSeenOfARoom(roomId);
	}
}
