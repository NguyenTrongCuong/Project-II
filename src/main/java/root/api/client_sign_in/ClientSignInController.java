package root.api.client_sign_in;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientSignInController {
	
	@GetMapping("/sign-in")
	public String signIn(@RequestParam(value="error", defaultValue="empty") String error,
						 @RequestParam(value="message", defaultValue="empty") String message, 
						 Model model) {
		model.addAttribute("error", error);
		model.addAttribute("message", message);
		return "sign-in/client-sign-in/client-sign-in-page";
	}

}
