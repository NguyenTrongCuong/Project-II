package root.entity_repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import root.entity.Notification;

@Repository
@Transactional
public interface NotificationRepository extends CrudRepository<Notification, Long> {
	
	@Modifying(flushAutomatically=true, clearAutomatically=true)
	@Query(value="UPDATE notifications SET is_seen = 1 WHERE ids = :id", nativeQuery=true)
	public void updateNotificationIsSeenOfANotification(@Param("id") Long id);

}
