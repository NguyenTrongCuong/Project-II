package root.api.client_sign_in;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import root.entity.ClientCredentials;
import root.entity_repository.ClientCredentialsRepository;

public class ClientDetailsService implements UserDetailsService {
	@Autowired
	private ClientCredentialsRepository clientCredentialsRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<ClientCredentials> result = this.clientCredentialsRepository.findByEmail(username);
		if(result.isEmpty()) {
			throw new UsernameNotFoundException(username + " is not found");
		}
		return new ClientDetails(result.get());
	}
	

}
















































