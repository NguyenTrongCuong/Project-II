package root.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.data.domain.Persistable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import root.annotation.ImageFile;
import root.annotation.UniqueClientEmail;

@Entity(name="clients")
public class Client implements Persistable<String>, Serializable {
	@Id
	@Column(name="emails")
	@NotBlank(message="Email is required")
	@Pattern(regexp="^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$", message="Invalid email")
	@UniqueClientEmail
	private String email;
	
	@Column(name="full_names")
	@NotBlank(message="Full name is required")
	private String fullName;
	
	@Column(name="dobs")
	private LocalDate dob;
	
	@Column(name="avatar_links")
	private String avatarLink;
	
	@Column(name="phone_numbers")
	@NotBlank(message="Phone number is required")
	@Pattern(regexp="(09|01[2|6|8|9])+([0-9]{8})", message="Invalid phone number")
	private String phoneNumber;
	
	@Embedded
	@Valid
	private Address address;
	
	@NotNull(message="Avatar is required")
	@ImageFile
	@Transient
	private MultipartFile avatar;
	
	@Transient
	@DateTimeFormat(pattern="MM/dd/yyy")
	@NotBlank(message="Date of birth is required")
	private String textDob;
	
	@Transient
	@NotBlank(message="Password is required")
	private String password;
	
	@Transient
	private boolean isNew = true;
	
	private static final long serialVersionUID = 1L;

	public String getTextDob() {
		return textDob;
	}

	public void setTextDob(String textDob) {
		this.textDob = textDob;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public MultipartFile getAvatar() {
		return avatar;
	}

	public void setAvatar(MultipartFile avatar) {
		this.avatar = avatar;
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

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getAvatarLink() {
		return avatarLink;
	}

	public void setAvatarLink(String avatarLink) {
		this.avatarLink = avatarLink;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public void formatDob(DateTimeFormatter formatter) {
		this.setDob(LocalDate.parse(this.getTextDob(), formatter));
	}

	@Override
	public String getId() {
		return this.email;
	}

	@Override
	public boolean isNew() {
		return this.isNew;
	}
	
	@PrePersist
	@PostLoad
	public void markNotNew() {
		this.isNew = false;
	}
	
	

}
