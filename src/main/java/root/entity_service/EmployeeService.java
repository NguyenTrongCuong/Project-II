package root.entity_service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import root.entity.Employee;
import root.entity_repository.EmployeeRepository;
import root.utils.EmployeeDTO;
import root.utils.SearchRequest;


@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public List<Employee> findAllEmployees() {
		return Lists.newArrayList(this.employeeRepository.findAll());
	}
	
	public Optional<List<Employee>> findEmployeesByPosition(String position) {
		return this.employeeRepository.findEmployeeByPosition(position);
	}
	
	public Employee updateEmployee(Employee employee) {
		return this.employeeRepository.save(employee);
	}
	
	public EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
		return new EmployeeDTO(employee);
	}
	
	public Optional<List<Employee>> findEmployeeByRegexp(SearchRequest request) {
		String regexp = "%" + request.getRegexp() + "%";
		return this.employeeRepository.findEmployeeByRegexp(regexp.toLowerCase());
	}

}



























