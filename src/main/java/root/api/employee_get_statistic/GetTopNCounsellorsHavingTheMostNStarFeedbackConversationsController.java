package root.api.employee_get_statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import root.entity_service.RoomService;
import root.utils.StatisticResponse;
import root.utils.TopNStatisticRequest;

@RestController
public class GetTopNCounsellorsHavingTheMostNStarFeedbackConversationsController {
	@Autowired
	private RoomService roomService;
	
	@PostMapping("/employee/get-top-n-counsellors")
	public StatisticResponse<Integer, String> getTopNCounsellorsHavingTheMostNStarFeedbackConversationsController(@RequestBody TopNStatisticRequest request) {
		return this.roomService.getTopNCounsellorsHavingTheMostNStarFeedbackConversations(request);
	}

}
