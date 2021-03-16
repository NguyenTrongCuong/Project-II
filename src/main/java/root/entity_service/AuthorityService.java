package root.entity_service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.entity.Authority;
import root.entity_repository.AuthorityRepository;

@Service
public class AuthorityService {
	@Autowired
	private AuthorityRepository authorityRepository;
	
	public Optional<Set<Authority>> findAuthoritiesById(List<String> roles) {
		return this.authorityRepository.findByRoleIn(roles);
	}
	
	public Iterable<Authority> updateAuthorities(Set<Authority> authority) {
		return this.authorityRepository.saveAll(authority);
	}
	
	
}
