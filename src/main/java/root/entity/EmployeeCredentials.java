package root.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

@Entity(name="credentials_of_employees")
public class EmployeeCredentials implements Persistable<String>, Serializable {
	@Id
	@Column(name="emails")
	private String email;
	
	@Column(name="passwords")
	private String password;
	
	@Column(name="is_account_non_expired")
	private boolean isAccountNonExpired = true;
	
	@Column(name="is_account_non_locked")
	private boolean isAccountNonLocked = true;
	
	@Column(name="is_credentials_non_expired")
	private boolean isCredentialsNonExpired = true;
	
	@ManyToMany(mappedBy="credentialsOfEmployee")
	private Set<Authority> authorities = new HashSet<>();
	
	@OneToOne
	@JoinColumn(name="employee_ids")
	private Employee employee;
	
	@Column(name="is_enabled")
	private boolean isEnabled = true;
	
	@Transient
	private boolean isNew = true;
	
	private static final long serialVersionUID = 1L;

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
