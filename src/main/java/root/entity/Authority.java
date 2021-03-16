package root.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

@Entity(name="authorities")
public class Authority implements Persistable<String>, Serializable {
	@Id
	@Column(name="roles")
	private String role;
	
	@ManyToMany
	@JoinTable(name="authorities_of_clients",
			   joinColumns=@JoinColumn(name="roles"),
			   inverseJoinColumns=@JoinColumn(name="clients_ids"))
	private Set<ClientCredentials> credentialsOfClient = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name="authorities_of_employees",
			   joinColumns=@JoinColumn(name="roles"),
			   inverseJoinColumns=@JoinColumn(name="employees_ids"))
	private Set<EmployeeCredentials> credentialsOfEmployee = new HashSet<>();
	
	@Transient
	private boolean isNew = true;
	
	private static final long serialVersionUID = 1L;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<ClientCredentials> getCredentialsOfClient() {
		return credentialsOfClient;
	}

	public void setCredentialsOfClient(Set<ClientCredentials> credentialsOfClient) {
		this.credentialsOfClient = credentialsOfClient;
	}

	public Set<EmployeeCredentials> getCredentialsOfEmployee() {
		return credentialsOfEmployee;
	}

	public void setCredentialsOfEmployee(Set<EmployeeCredentials> credentialsOfEmployee) {
		this.credentialsOfEmployee = credentialsOfEmployee;
	}

	@Override
	public String getId() {
		return this.role;
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





























































