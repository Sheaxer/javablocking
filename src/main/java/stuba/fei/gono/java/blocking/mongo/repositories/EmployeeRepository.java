package stuba.fei.gono.java.blocking.mongo.repositories;

import org.springframework.data.repository.CrudRepository;
import stuba.fei.gono.java.pojo.Employee;

import java.util.Optional;
/***
 * <div class="en">Interface extending CrudRepository for Employee.</div>
 * <div class="sk">Rozhranie rozširujúce CrudRepository pre objekty triedy Employee.</div>
 * @see Employee
 * @see CrudRepository
 */
public interface EmployeeRepository extends CrudRepository<Employee, String> {
    /***
     * <div class="en">Finds the entity with the given user name.</div>
     * <div class="sk">Nájde entitu so zadaným používateľským menom.</div>
     * @param username <div class="en">user name.</div>
     *                 <div class="sk">používateľské meno.</div>
     * @return <div class="en">Optional containing the entity or Optional.empty() if no entity is found.</div>
     * <div class="sk">Optional obsahujúci entitu alebo Optional.empty(), ak entita nebola nájdená.</div>
     */
    Optional<Employee> findEmployeeByUsername(String username);

    /***
     * <div class="en">Checks if entity with the given user name exists.</div>
     * <div class="sk">Skontroluje či entita so zadaným používateľským menom existuje.</div>
     * @param username <div class="en">user name.</div>
     *                 <div class="sk">používateľské meno.</div>
     * @return <div class="en">true if entity exists, false otherwise.</div>
     * <div class="sk">true ak entita existuje, false inak.</div>
     */
    Boolean existsByUsername(String username);
}
