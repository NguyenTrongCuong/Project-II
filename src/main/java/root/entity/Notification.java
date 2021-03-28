package root.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

@Entity(name="notifications")
@Inheritance(strategy=InheritanceType.JOINED)
public class Notification implements Persistable<Long>, Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ids")
	private Long id;
	
	@Column(name="is_seen")
	private int isSeen = 0;
	
	@Column(name="types")
	private String type = "";
	
	public Notification() {
		super();
	}

	@Transient
	private static final long serialVersionUID = 1L;
	
	@Transient
	private boolean isNew = true;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIsSeen() {
		return isSeen;
	}

	public void setIsSeen(int isSeen) {
		this.isSeen = isSeen;
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
