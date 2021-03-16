package root.api.client_invalid_session;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClientProcessInvalidSessionController {
	
	@GetMapping("/invalid-session")
	public ModelAndView processInvalidSession(ModelMap model) {
		model.addAttribute("message", "Please sign in to continue");
		return new ModelAndView("redirect:/sign-in", model);
	}

}
