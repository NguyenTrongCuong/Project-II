package root.api.employee_sign_in;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import root.entity.Authority;
import root.entity.EmployeeCredentials;

public class EmployeeDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
	private EmployeeCredentials employeeCredentials;
	private List<GrantedAuthority> authorities = new ArrayList<>();
	
	public EmployeeDetails(EmployeeCredentials employeeCredentials) {
		this.employeeCredentials = employeeCredentials;
		this.setAuthorities();
	}
	
	private void setAuthorities() {
		Set<Authority> authorityArr = this.employeeCredentials.getAuthorities();
		for(Authority element : authorityArr) {
			this.authorities.add(new SimpleGrantedAuthority(element.getRole()));
		}
	}

	public EmployeeCredentials getEmployeeCredentials() {
		return employeeCredentials;
	}

	public void setEmployeeCredentials(EmployeeCredentials employeeCredentials) {
		this.employeeCredentials = employeeCredentials;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.employeeCredentials.getPassword();
	}

	@Override
	public String getUsername() {
		return this.employeeCredentials.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.employeeCredentials.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.employeeCredentials.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.employeeCredentials.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return this.employeeCredentials.isEnabled();
	}

}
