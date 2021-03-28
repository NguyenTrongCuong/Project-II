package root.entity_repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import root.entity.Room;

@Repository
public interface RoomRepository extends PagingAndSortingRepository<Room, Long> {
	
	@Query("SELECT r FROM rooms r JOIN r.client c WHERE c.email = :clientEmail")
	public Page<Room> findLatestRoomOfClient(String clientEmail, Pageable pageable);
	
	@Query("SELECT DISTINCT r FROM rooms r JOIN r.employee e LEFT JOIN FETCH r.messages m WHERE e.email = :employeeEmail ORDER BY r.id DESC")
	public Optional<List<Room>> findAllRoomsThatEmployeeJoinedIn(String employeeEmail);
	
	@Query("SELECT DISTINCT r FROM rooms r JOIN r.employee e LEFT JOIN FETCH r.messages m WHERE e.email = :employeeEmail AND DAY(r.createdAt) = :day AND MONTH(r.createdAt) = :month AND YEAR(r.createdAt) = :year ORDER BY r.id DESC")
	public Optional<List<Room>> findAllRoomsThatEmployeeJoinedInByDay(String employeeEmail, int day, int month, int year);
	
	@Query("SELECT DISTINCT r FROM rooms r JOIN r.employee e LEFT JOIN FETCH r.messages m WHERE e.email = :employeeEmail AND MONTH(r.createdAt) = :month AND YEAR(r.createdAt) = :year ORDER BY r.id DESC")
	public Optional<List<Room>> findAllRoomsThatEmployeeJoinedByMonth(String employeeEmail, int month, int year);
	
	@EntityGraph(attributePaths={"messages", "client", "employee"}, type=EntityGraph.EntityGraphType.FETCH)
	public Optional<Room> findRoomById(long roomId);
	
	@Query(value="SELECT temp.sf, COUNT(temp.sf) FROM (SELECT r.star_feedbacks sf FROM rooms r WHERE MONTH(r.created_at) = :month AND YEAR(r.created_at) = :year) temp GROUP BY temp.sf", nativeQuery=true)
	public Optional<List<Object[]>> findTotalNumberOfConversationsCategorizedByStarFeedbackAndMonth(@Param("month") int month, @Param("year") int year);
	
	@Query(value="SELECT temp.sf, COUNT(temp.sf) FROM (SELECT r.star_feedbacks sf FROM rooms r WHERE DAY(r.created_at) = :day AND MONTH(r.created_at) = :month AND YEAR(r.created_at) = :year) temp GROUP BY temp.sf", nativeQuery=true)
	public Optional<List<Object[]>> findTotalNumberOfConversationsCategorizedByStarFeedbackAndDay(@Param("day") int day, @Param("month") int month, @Param("year") int year);
	
	@Query(value="SELECT temp.name, COUNT(temp.email) AS quantity FROM (SELECT e.emails AS email, e.full_names AS name FROM rooms r JOIN employees e ON r.employee_ids = e.emails WHERE DAY(r.created_at) = :day AND MONTH(r.created_at) = :month AND YEAR(r.created_at) = :year AND r.star_feedbacks = :star) AS temp GROUP BY temp.email ORDER BY quantity DESC LIMIT :topNCounsellor", nativeQuery=true)
	public Optional<List<Object[]>> findTopNCounsellorsHavingTheMostNStarFeedbackConversationsByDay(@Param("day") int day, @Param("month") int month, @Param("year") int year, @Param("star") String star, @Param("topNCounsellor") int topNCounsellor);
	
	@Query(value="SELECT temp.name, COUNT(temp.email) AS quantity FROM (SELECT e.emails AS email, e.full_names AS name FROM rooms r JOIN employees e ON r.employee_ids = e.emails WHERE MONTH(r.created_at) = :month AND YEAR(r.created_at) = :year AND r.star_feedbacks = :star) AS temp GROUP BY temp.email ORDER BY quantity DESC LIMIT :topNCounsellor", nativeQuery=true)
	public Optional<List<Object[]>> findTopNCounsellorsHavingTheMostNStarFeedbackConversationsByMonth(@Param("month") int month, @Param("year") int year, @Param("star") String star, @Param("topNCounsellor") int topNCounsellor);
	
}









































