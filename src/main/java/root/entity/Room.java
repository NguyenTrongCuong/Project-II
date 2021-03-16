package root.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

@Entity(name="rooms")
public class Room implements Persistable<Long>, Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ids")
	private Long id;
	
	@Column(name="names")
	private String name;
	
	@Column(name="created_at")
	private Instant createdAt;
	
	@Column(name="text_feedbacks")
	private String textFeedback;
	
	@Column(name="star_feedbacks")
	private String starFeedback;
	
	@Column(name="is_closed")
	private boolean isClosed = false;
	
	@ManyToOne
	@JoinColumn(name="client_ids")
	private Client client;
	
	@ManyToOne
	@JoinColumn(name="employee_ids")
	private Employee employee;
	
	@OneToMany(mappedBy="room")
	private Set<Message> messages = new HashSet<>();
	
	@Transient
	private boolean isNew = true;
	
	private static final long serialVersionUID = 1L;

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public String getTextFeedback() {
		return textFeedback;
	}

	public void setTextFeedback(String textFeedback) {
		this.textFeedback = textFeedback;
	}

	public String getStarFeedback() {
		return starFeedback;
	}

	public void setStarFeedback(String starFeedback) {
		this.starFeedback = starFeedback;
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
