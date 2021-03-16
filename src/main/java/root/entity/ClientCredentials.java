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

import root.password_encoder.PasswordEncoder;

@Entity(name="credentials_of_clients")
public class ClientCredentials implements Persistable<String>, Serializable {
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
	
	@Column(name="is_enabled")
	private boolean isEnabled = true;
	
	@ManyToMany(mappedBy="credentialsOfClient")
	private Set<Authority> authorities = new HashSet<>();
	
	@OneToOne
	@JoinColumn(name="user_ids")
	private Client client;
	
	@Transient
	private boolean isNew = true;
	
	private static final long serialVersionUID = 1L;

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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
	
	public void hashPassword(PasswordEncoder encoder, int salt) {
		String hashedPassword = encoder.hash(this.getPassword(), salt);
		this.setPassword(hashedPassword);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		ClientCredentials other = (ClientCredentials) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	

}
