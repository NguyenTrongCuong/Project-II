package root.entity_service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import root.entity.Room;
import root.entity_repository.RoomRepository;
import root.utils.RoomDTO;

@Service
public class RoomService {
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private EmployeeService employeeService;
	
	public Page<Room> findLatestRoomOfClient(String clientEmail, Pageable pageable) {
		return this.roomRepository.findLatestRoomOfClient(clientEmail, pageable);
	}
	
	public Room saveRoom(Room room) {
		return this.roomRepository.save(room);
	}
	
	public RoomDTO convertRoomToRoomDTO(Room room) {
		return new RoomDTO(room, messageService, clientService, employeeService);
	}
	
	public Optional<List<Room>> findAllRoomsThatEmployeeJoinedIn(String employeeEmail) {
		return this.roomRepository.findAllRoomsThatEmployeeJoinedIn(employeeEmail);
	}
	
	public Optional<Room> findRoomWithMessagesLoadedEagerly(long roomId) {
		return this.roomRepository.findRoomById(roomId);
	}
	
	public Room updateRoom(Room room) {
		return this.roomRepository.save(room);
	}
	
	public Optional<Room> findRoomById(long roomId) {
		return this.roomRepository.findById(roomId);
	}
	
}

























