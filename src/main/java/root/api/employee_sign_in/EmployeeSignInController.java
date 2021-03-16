package root.api.employee_sign_in;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmployeeSignInController {
	
	@GetMapping("/employee/sign-in")
	public String signIn(@RequestParam(value="error", defaultValue="empty") String error,
						 @RequestParam(value="message", defaultValue="empty") String message,
						 Model model) {
		model.addAttribute("error", error);
		model.addAttribute("message", message);
		return "sign-in/employee-sign-in/employee-sign-in-page";
	}
	

}














































































