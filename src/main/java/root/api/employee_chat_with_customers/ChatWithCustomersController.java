package root.api.employee_chat_with_customers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import root.api.employee_sign_in.EmployeeDetails;
import root.entity.Room;
import root.entity_service.RoomService;
import root.utils.RoomDTO;

@Controller
public class ChatWithCustomersController {
	@Autowired
	private RoomService roomService;
	
	@GetMapping("/employee/chat-with-customers")
	public String chatWithCustomers(@AuthenticationPrincipal EmployeeDetails employeeDetails, Model model) {
		String employeeEmail = employeeDetails.getUsername();
		Optional<List<Room>> joinedRooms = this.roomService.findAllRoomsThatEmployeeJoinedIn(employeeEmail);
		List<RoomDTO> joinedRoomsArr = new ArrayList<>();
		if(!joinedRooms.isEmpty()) {
			List<Room> rooms = joinedRooms.get();
			for(Room room : rooms) {
				joinedRoomsArr.add(this.roomService.convertRoomToRoomDTO(room));
			}
		}
		model.addAttribute("joinedRooms", joinedRoomsArr);
		return "customer-care/employee-customer-care/employee-customer-care-page";
	}

}






















































