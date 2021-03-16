package root.api.employee_sign_in;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import root.entity.EmployeeCredentials;
import root.entity_repository.EmployeeCredentialsRepository;


public class EmployeeDetailsService implements UserDetailsService {
	@Autowired
	private EmployeeCredentialsRepository employeeCredentialsRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<EmployeeCredentials> result = this.employeeCredentialsRepository.findByEmail(username);
		if(result.isEmpty()) {
			throw new UsernameNotFoundException(username + " is not found");
		}
		return new EmployeeDetails(result.get());
	}

}































































