package root.utils;

import root.entity.Employee;

public class EmployeeDTO {
	private String email;
	private String fullName;
	private String avatarLink;
	
	public EmployeeDTO(Employee employee) {
		this.email = employee.getEmail();
		this.fullName = employee.getFullName();
		this.avatarLink = employee.getAvatarLink();
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
