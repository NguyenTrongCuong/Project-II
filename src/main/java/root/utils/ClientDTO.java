package root.utils;

import root.entity.Client;

public class ClientDTO {
	private String email;
	private String fullName;
	private String avatarLink;
	
	public ClientDTO(Client client) {
		this.email = client.getEmail();
		this.fullName = client.getFullName();
		this.avatarLink = client.getAvatarLink();
	}
	
	public String getAvatarLink() {
		return avatarLink;
	}

	public void setAvatarLink(String avatarLink) {
		this.avatarLink = avatarLink;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	

}
