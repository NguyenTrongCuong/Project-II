package root.utils;

public class NotificationDTO {
	private Long id;
	
	private int isSeen;
	
	private String type;
	
	public NotificationDTO() {}

	public NotificationDTO(Long id, int isSeen, String type) {
		super();
		this.id = id;
		this.isSeen = isSeen;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getIsSeen() {
		return isSeen;
	}

	public void setIsSeen(int isSeen) {
		this.isSeen = isSeen;
	}
	
	

}
