package root.entity_repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import root.entity.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
	
	@Query("SELECT m FROM messages m JOIN m.room r WHERE r.id = :roomId ORDER BY m.id ASC")
	public Optional<List<Message>> findMessagesOfRoom(long roomId);
	
}
