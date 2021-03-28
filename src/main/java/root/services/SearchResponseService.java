package root.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.entity.Employee;
import root.entity_service.EmployeeService;
import root.utils.EmployeeDTO;
import root.utils.SearchResponse;

@Service
public class SearchResponseService {
	@Autowired
	private EmployeeService employeeService;
	
	public SearchResponse<EmployeeDTO> buildSearchResponseForSearchEmployeeByRegexp(Optional<List<Employee>> result) {
		SearchResponse<EmployeeDTO> response = new SearchResponse<>();
		if(!result.isEmpty()) {
			List<Employee> employees = result.get();
			employees.stream().forEach(employee -> response.getResult().add(this.employeeService.convertEmployeeToEmployeeDTO(employee)));
		}
		return response;
	}

}
