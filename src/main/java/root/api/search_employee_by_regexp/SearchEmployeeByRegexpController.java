package root.api.search_employee_by_regexp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import root.entity.Employee;
import root.entity_service.EmployeeService;
import root.services.SearchResponseService;
import root.utils.EmployeeDTO;
import root.utils.SearchRequest;
import root.utils.SearchResponse;

@RestController
public class SearchEmployeeByRegexpController {
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private SearchResponseService searchResponseService;
	
	@PostMapping("/employee/search-by-regexp")
	public SearchResponse<EmployeeDTO> handleSearchEmployeeByRegexp(@RequestBody SearchRequest request) {
		Optional<List<Employee>> result = this.employeeService.findEmployeeByRegexp(request);
		
		return this.searchResponseService.buildSearchResponseForSearchEmployeeByRegexp(result);
	}

}












































