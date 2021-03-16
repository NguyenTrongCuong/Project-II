package root.api.employee_home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeHomeController {
	
	@GetMapping("/employee")
	public String home() {
		return "home/employee-home/employee-home-page";
	}

}






















































