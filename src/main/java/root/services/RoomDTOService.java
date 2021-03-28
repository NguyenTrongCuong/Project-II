package root.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.entity.Room;
import root.entity_service.ClientService;
import root.entity_service.EmployeeService;
import root.utils.RoomDTO;

@Service
public class RoomDTOService {
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private EmployeeService employeeService;
	
	public RoomDTO convertRoomToRoomDTO(Room room) {
		return new RoomDTO(room, clientService, employeeService);
	}
	
	

}
