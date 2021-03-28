package root.api.employee_management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagementController {
	
	@GetMapping("/employee/management")
	public String management() {
		return "management/employee-management/employee-management-page";
	}

}
