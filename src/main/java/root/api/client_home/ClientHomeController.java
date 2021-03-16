package root.api.client_home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientHomeController {
	
	
	@GetMapping("/")
	public String home() {
		return "home/client-home/client-home-page";
	}

}
