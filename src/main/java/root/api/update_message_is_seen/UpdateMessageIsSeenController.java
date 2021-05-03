package root.api.update_message_is_seen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import root.entity_service.MessageService;

@RestController
public class UpdateMessageIsSeenController {
	@Autowired
	private MessageService messageService;
	
	@PutMapping("/employee/update-message-is-seen/{messageId}")
	public void updateMessageIsSeen(@PathVariable("messageId") long messageId) {
		
		this.messageService.updateMessageIsSeenById(messageId, 1);
		
	}

}
