package root.api.employee_get_statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import root.entity_service.RoomService;
import root.utils.StatisticRequest;
import root.utils.StatisticResponse;

@RestController
public class GetConversationsCategorizedByStarFeedbackController {
	@Autowired
	private RoomService roomService;
	
	@PostMapping("/employee/get-consultations-categorized-by-star-feedback")
	public StatisticResponse<Integer, String> getTotalNumberOfConversationsCategorizedByStarFeedback(@RequestBody StatisticRequest request) {
		return this.roomService.getTotalNumberOfRoomsCategorizedByStarFeedback(request);
	}

}
































