package root.utils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import root.entity.Message;
import root.entity_service.ClientService;
import root.entity_service.EmployeeService;

public class MessageDTO {
	private Long id;
	private Long roomId;
	private String content;
	private String sentAt;
	private String senderEmail;
	private String senderFullName;
	private String senderAvatarLink;
	private int isBinary;
	private int isSeen;
	
	public MessageDTO(Message message, ClientService clientService, EmployeeService employeeService) {
		this.id = message.getId();
		this.roomId = message.getRoom().getId();
		this.content = message.getContent();
		this.isBinary = message.isBinary() ? 1 : 0;
		this.isSeen = message.getIsSeen();
		this.sentAt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
	            						.withZone(ZoneId.of("Asia/Ho_Chi_Minh"))
	            						.format(message.getSentAt());
		this.senderEmail = message.getSenderEmail();
		this.senderFullName = message.getSenderFullName();
		this.senderAvatarLink = message.getSenderAvatarLink();
	}

	public int getIsSeen() {
		return isSeen;
	}

	public void setIsSeen(int isSeen) {
		this.isSeen = isSeen;
	}

	public Long getRoomId() {
		return roomId;
	}


	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}


	public String getSenderAvatarLink() {
		return senderAvatarLink;
	}


	public void setSenderAvatarLink(String senderAvatarLink) {
		this.senderAvatarLink = senderAvatarLink;
	}


	public String getSenderEmail() {
		return senderEmail;
	}


	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}


	public String getSenderFullName() {
		return senderFullName;
	}


	public void setSenderFullName(String senderFullName) {
		this.senderFullName = senderFullName;
	}

	public int getIsBinary() {
		return isBinary;
	}

	public void setIsBinary(int isBinary) {
		this.isBinary = isBinary;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSentAt() {
		return sentAt;
	}

	public void setSentAt(String sentAt) {
		this.sentAt = sentAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageDTO other = (MessageDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
