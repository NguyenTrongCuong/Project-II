package root.entity_repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import root.entity.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, String> {

}
