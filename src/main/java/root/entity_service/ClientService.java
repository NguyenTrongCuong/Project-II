package root.entity_service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.entity.Client;
import root.entity_repository.ClientRepository;
import root.utils.ClientDTO;

@Service
public class ClientService {
	@Autowired
	private ClientRepository clientRepository;
	
	public Client saveClient(Client client) {
		return this.clientRepository.save(client);
	}
	
	public Optional<Client> findClientById(String email) {
		return this.clientRepository.findById(email);
	}
	
	public ClientDTO convertClientToClientDTO(Client client) {
		return new ClientDTO(client);
	}
	

}
