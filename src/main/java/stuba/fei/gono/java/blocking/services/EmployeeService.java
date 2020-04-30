package stuba.fei.gono.java.blocking.services;

import stuba.fei.gono.java.pojo.Employee;

import java.util.Optional;

/***
 * Interface for marshalling and de-marshalling Employee entities.
 */
public interface EmployeeService {

    /***
     * Finds the entity with the given user name.
     * @param userName user name of the entity.
     * @return Optional containing the entity or Optional.empty() if no entity was found.
     */
    Optional<Employee> findEmployeeByUsername(String userName);

    /***
     * Finds the entity with the given id.
     * @param id id of entity.
     * @return Optional containing the entity or Optional.empty() if no entity was found.
     */
    Optional<Employee> getEmployeeById(String id);

    /***
     * Saves the entity.
     * @param employee entity to be saved.
     * @return true if saved, false if not - employee already exists.
     */
    boolean saveEmployee(Employee employee);

}
