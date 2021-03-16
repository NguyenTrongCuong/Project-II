package root.entity_repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import root.entity.Room;

@Repository
public interface RoomRepository extends PagingAndSortingRepository<Room, Long> {
	
	@Query("SELECT r FROM rooms r JOIN r.client c WHERE c.email = :clientEmail")
	public Page<Room> findLatestRoomOfClient(String clientEmail, Pageable pageable);
	
	@Query("SELECT DISTINCT r FROM rooms r JOIN r.employee e LEFT JOIN FETCH r.messages m WHERE e.email = :employeeEmail ORDER BY r.id DESC")
	public Optional<List<Room>> findAllRoomsThatEmployeeJoinedIn(String employeeEmail);
	
	@EntityGraph(attributePaths={"messages"}, type=EntityGraph.EntityGraphType.FETCH)
	public Optional<Room> findRoomById(long roomId);

}
