package root.entity_repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import root.entity.EmployeeCredentials;

@Repository
public interface EmployeeCredentialsRepository extends CrudRepository<EmployeeCredentials, String> {
	
	@EntityGraph(attributePaths={"authorities", "employee"}, type=EntityGraph.EntityGraphType.FETCH)
	public Optional<EmployeeCredentials> findByEmail(String email);

}
