package root.entity_service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.entity.Client;
import root.entity.ClientCredentials;
import root.entity_repository.ClientCredentialsRepository;
import root.password_encoder.BCryptPasswordEncoder;

@Service
public class ClientCredentialsService {
	@Autowired
	private ClientCredentialsRepository clientCredentialsRepository;
	
	public boolean isEmailUnique(String email) {
		return this.clientCredentialsRepository.isEmailUnique(email) > 0 ? false : true;
	}
	
	public ClientCredentials convertClientToClientCredentials(Client client) {
		ClientCredentials clientCredentials = new ClientCredentials();
		clientCredentials.setEmail(client.getEmail());
		clientCredentials.setPassword(client.getPassword());
		clientCredentials.hashPassword(new BCryptPasswordEncoder(), 10);
		clientCredentials.setAccountNonExpired(true);
		clientCredentials.setAccountNonLocked(true);
		clientCredentials.setCredentialsNonExpired(true);
		clientCredentials.setEnabled(true);
		return clientCredentials;
	}
	
	public ClientCredentials saveClientCredentials(ClientCredentials clientCredentials) {
		return this.clientCredentialsRepository.save(clientCredentials);
	}
	
	public Optional<ClientCredentials> findClientCredentialsWithAuthoritiesById(String email) {
		return this.clientCredentialsRepository.findByEmail(email);
	}

}


















