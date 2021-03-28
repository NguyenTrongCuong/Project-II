package root.api.employee_get_statistic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import root.entity.Room;
import root.entity_service.RoomService;
import root.utils.JoinedInRoomsRequest;
import root.utils.RoomDTO;

@RestController
public class GetJoinedInRoomsOfACounsellor {
	@Autowired
	private RoomService roomService;
	
	@PostMapping("/employee/get-joined-in-rooms")
	public List<RoomDTO> getJoinedInRoomsOfACounsellor(@RequestBody JoinedInRoomsRequest request) {
		Optional<List<Room>> result = this.roomService.findAllRoomsThatEmployeeJoinedInByTime(request);
		List<RoomDTO> roomsDTO = new ArrayList<RoomDTO>();
		if(!result.isEmpty()) {
			List<Room> rooms = result.get();
			rooms.stream().forEach(room -> roomsDTO.add(this.roomService.convertRoomToRoomDTO(room)));
		}
		return roomsDTO;
	}

}


































