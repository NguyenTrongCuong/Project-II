package root.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.entity.MessageFromClientNotification;
import root.utils.MessageFromClientNotificationDTO;

@Service
public class MessageFromClientNotificationDTOService {

	@Autowired
	private RoomDTOService roomDTOService;
	
	public List<MessageFromClientNotificationDTO> convertMessageFromClientNotificationsToMessageFromClientNotificationsDTO(Optional<List<MessageFromClientNotification>> result) {
		List<MessageFromClientNotificationDTO> notificationsDTO = new ArrayList<>();
		
		if(!result.isEmpty()) {
			List<MessageFromClientNotification> notifications = result.get();
			notifications.stream().forEach(notification -> notificationsDTO.add(this.convertMessageFromClientNotificationToMessageFromClientNotificationDTO(notification)));
		}
		
		return notificationsDTO;
	}
	
	public MessageFromClientNotificationDTO convertMessageFromClientNotificationToMessageFromClientNotificationDTO(MessageFromClientNotification notification) {
		return new MessageFromClientNotificationDTO(notification, roomDTOService);
	}

}
