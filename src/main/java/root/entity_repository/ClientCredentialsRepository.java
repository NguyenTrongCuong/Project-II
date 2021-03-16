package root.entity_repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import root.entity.ClientCredentials;

@Repository
public interface ClientCredentialsRepository extends CrudRepository<ClientCredentials, String> {
	
	@Query("SELECT COUNT(c.email) FROM credentials_of_clients c WHERE c.email = :email")
	public Integer isEmailUnique(@Param("email") String email);
	
	@EntityGraph(attributePaths= {"authorities", "client"}, type=EntityGraph.EntityGraphType.FETCH)
	public Optional<ClientCredentials> findByEmail(String email);

}
