package root.amqp_service;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AmqpService {
	@Autowired
	@Qualifier("amqpAdmin")
	private AmqpAdmin amqpAdmin;
	
	public void declareMessageNotificationQueue(String email) {
		Queue messageNotificationQueue = new Queue("message-notification-of-" + email);
		this.amqpAdmin.declareQueue(messageNotificationQueue);
	}
}
