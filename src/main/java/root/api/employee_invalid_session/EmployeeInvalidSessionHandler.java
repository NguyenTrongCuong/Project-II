package root.api.employee_invalid_session;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmployeeInvalidSessionHandler {
	
	@GetMapping("/employee/invalid-session")
	public ModelAndView handle(ModelMap model) {
		model.addAttribute("message", "Please sign in to continue");
		return new ModelAndView("redirect:/employee/sign-in", model);
	}

}
