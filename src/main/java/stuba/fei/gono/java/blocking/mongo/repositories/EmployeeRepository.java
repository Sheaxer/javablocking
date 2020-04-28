package stuba.fei.gono.java.blocking.mongo.repositories;

import org.springframework.data.repository.CrudRepository;
import stuba.fei.gono.java.pojo.Employee;

import java.util.Optional;
/***
 * Interface extending CrudRepository for Employee.
 * @see Employee
 * @see CrudRepository
 */
public interface EmployeeRepository extends CrudRepository<Employee, String> {
    /***
     * Finds the entity with the given user name.
     * @param username User
     * @return Optional containing the entity or Optional.empty() if no entity is found.
     */
    Optional<Employee> findEmployeeByUsername(String username);

    Boolean existsByUsername(String username);
}
