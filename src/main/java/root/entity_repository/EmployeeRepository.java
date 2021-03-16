package root.entity_repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import root.entity.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String> {
	
	@EntityGraph(attributePaths={"rooms"}, type=EntityGraph.EntityGraphType.FETCH)
	public Iterable<Employee> findAll();
	
	@EntityGraph(attributePaths={"rooms"}, type=EntityGraph.EntityGraphType.FETCH)
	public Optional<List<Employee>> findEmployeeByPosition(String position);

}
