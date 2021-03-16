package root.entity_repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import root.entity.Authority;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, String> {
	
	@EntityGraph(attributePaths="credentialsOfClient", type=EntityGraph.EntityGraphType.FETCH)
	public Optional<Set<Authority>> findByRoleIn(List<String> roles);

}
