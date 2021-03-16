package root.entity;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;
import org.springframework.web.multipart.MultipartFile;

@Entity(name="messages")
public class Message implements Persistable<Long>, Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ids")
	private Long id;
	
	@Column(name="contents")
	private String content;
	
	@Column(name="senders_full_names")
	private String senderFullName;
	
	@Column(name="senders_emails")
	private String senderEmail;
	
	@Column(name="senders_avatar_links")
	private String senderAvatarLink;
	
	@Column(name="sent_at")
	private Instant sentAt;
	
	@Column(name="is_binary")
	private boolean isBinary;
	
	@ManyToOne
	@JoinColumn(name="room_ids")
	private Room room;
	
	@Transient
	private MultipartFile[] files;
	
	@Transient
	private String textContent;
	
	@Transient
	private boolean isNew = true;
	
	private static final long serialVersionUID = 1L;

	public String getSenderAvatarLink() {
		return senderAvatarLink;
	}

	public void setSenderAvatarLink(String senderAvatarLink) {
		this.senderAvatarLink = senderAvatarLink;
	}

	public String getSenderFullName() {
		return senderFullName;
	}

	public void setSenderFullName(String senderFullName) {
		this.senderFullName = senderFullName;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public boolean isBinary() {
		return isBinary;
	}

	public void setBinary(boolean isBinary) {
		this.isBinary = isBinary;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Instant getSentAt() {
		return sentAt;
	}

	public void setSentAt(Instant sentAt) {
		this.sentAt = sentAt;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return this.id;
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
