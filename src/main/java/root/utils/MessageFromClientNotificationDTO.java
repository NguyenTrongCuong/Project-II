package root.utils;

import root.entity.MessageFromClientNotification;
import root.services.RoomDTOService;

public class MessageFromClientNotificationDTO extends NotificationDTO {
	private RoomDTO room;

	public MessageFromClientNotificationDTO() {
		super();
	}

	public MessageFromClientNotificationDTO(MessageFromClientNotification notification, RoomDTOService roomDTOService) {
		super(notification.getId(), notification.getIsSeen(), notification.getType());
		this.room = roomDTOService.convertRoomToRoomDTO(notification.getRoom());
	}

	public RoomDTO getRoom() {
		return room;
	}

	public void setRoom(RoomDTO room) {
		this.room = room;
	}
	
	
	

}
