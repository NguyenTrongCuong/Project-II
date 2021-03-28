package root.entity_repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import root.entity.MessageFromClientNotification;

@Repository
@Transactional
public interface MessageFromClientNotificationRepository extends CrudRepository<MessageFromClientNotification, Long> {
	
	@Query("SELECT mn FROM message_from_client_notifications mn JOIN mn.room r JOIN r.employee e WHERE e.email = :employeeEmail AND mn.isSeen = 0")
	public Optional<List<MessageFromClientNotification>> findUnseenMessageNotificationsOfAnEmployee(@Param("employeeEmail") String employeeEmail);
	
	@Modifying(flushAutomatically=true, clearAutomatically=true)
	@Query(value="UPDATE notifications n SET n.is_seen = 1 WHERE n.ids IN (SELECT mn.ids FROM message_from_client_notifications mn WHERE mn.room_ids = :roomId)", nativeQuery=true)
	public void updateMessageNotificationIsSeenOfARoom(@Param("roomId") Long roomId);

}
