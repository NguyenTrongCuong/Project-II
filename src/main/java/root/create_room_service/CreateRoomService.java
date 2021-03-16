package root.create_room_service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import root.entity.Client;
import root.entity.Employee;
import root.entity.Room;
import root.entity_service.ClientService;
import root.entity_service.EmployeeService;
import root.entity_service.RoomService;

@Service
public class CreateRoomService {
	@Autowired
	private RoomService roomService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private EmployeeService employeeService;
	
	public Room createRoom(String clientEmail) {
		//get the client
		Client client = this.clientService.findClientById(clientEmail).get();
		
		//get the counsellor who is going to chat with the client
		Employee employee = this.performRoundRobin(clientEmail);
		
		//create a new room for those two
		Room room = new Room();
		room.setClient(client);
		room.setEmployee(employee);
		room.setCreatedAt(Instant.now());
		room.setName("Conversation between " + client.getFullName() + " and " + employee.getFullName());
		room.setClosed(false);
		employee.getRooms().add(room);
		room = this.roomService.saveRoom(room);
		this.employeeService.updateEmployee(employee);
		return room;
	}
	
	private Employee performRoundRobin(String clientEmail) {
		//get the room which the client last joined
		Page<Room> room = this.roomService.findLatestRoomOfClient(clientEmail, PageRequest.of(0, 1, Sort.by("createdAt").descending()));
		
		//get all the counsellors in the queue
		List<Employee> counsellors = this.employeeService.findEmployeesByPosition("Counsellor").get();
		
		//get the next counsellor in the queue who stands after the last counsellor the client chatted with
		if(room.isEmpty()) {
			return counsellors.get(0);
		}
		Room result = room.toList().get(0);
		int position = counsellors.indexOf(result.getEmployee()) + 1;
		int totalCounsellors = counsellors.size();
		return counsellors.get(position % totalCounsellors);
	}

}



































































