package root.api.client_sign_in;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import root.entity.Authority;
import root.entity.ClientCredentials;

public class ClientDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
	private List<GrantedAuthority> authorities = new ArrayList<>();
	private ClientCredentials credentialsOfClient;
	
	public ClientDetails() {}
	
	public ClientDetails(ClientCredentials credentialsOfClient) {
		this.credentialsOfClient = credentialsOfClient;
		setAuthorities();
	}
	
	private void setAuthorities() {
		Set<Authority> authoritiesArr = this.credentialsOfClient.getAuthorities();
		for(Authority authority : authoritiesArr) {
			this.authorities.add(new SimpleGrantedAuthority(authority.getRole()));
		}
	}
	
	public ClientCredentials getCredentialsOfClient() {
		return credentialsOfClient;
	}

	public void setCredentialsOfClient(ClientCredentials credentialsOfClient) {
		this.credentialsOfClient = credentialsOfClient;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.credentialsOfClient.getPassword();
	}

	@Override
	public String getUsername() {
		return this.credentialsOfClient.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.credentialsOfClient.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.credentialsOfClient.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsOfClient.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return this.credentialsOfClient.isEnabled();
	}

}
