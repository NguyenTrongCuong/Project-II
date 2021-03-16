package root.api.client_customer_care_services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerCareServices {
	
	@GetMapping("/customer-care-services")
	public String customerCareServices() {
		return "customer-care/client-customer-care/client-customer-care-page";
	}

}
