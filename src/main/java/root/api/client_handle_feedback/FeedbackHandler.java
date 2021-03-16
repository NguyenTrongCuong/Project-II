package root.api.client_handle_feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import root.entity.Room;
import root.entity_service.RoomService;
import root.utils.Feedback;

@RestController
public class FeedbackHandler {
	@Autowired
	private RoomService roomService;
	
	@PutMapping("/give-feedback/{roomId}")
	public void handleFeedback(@RequestBody Feedback feedbackDTO, @PathVariable("roomId") long roomId) {
		Room room = this.roomService.findRoomById(roomId).get();
		room.setTextFeedback(feedbackDTO.getTextFeedback());
		room.setStarFeedback(feedbackDTO.getStarFeedback());
		this.roomService.updateRoom(room);
	}

}





























































