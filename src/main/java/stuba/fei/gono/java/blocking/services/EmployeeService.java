package stuba.fei.gono.java.blocking.services;

import stuba.fei.gono.java.pojo.Employee;

import java.util.Optional;

/***
 * <div class="en">Interface for marshalling and de-marshalling Employee entities.</div>
 * <div class="sk">Rozhranie pre mashalling a de-marshalling entít triedy Employee.</div>
 */
public interface EmployeeService {

    /***
     * <div class="en">Finds the entity with the given user name.</div>
     * <div class="sk">Nájde entitu so zadaným použivateľským menom.</div>
     * @param userName <div class="en">user name of the entity.</div>
     *                 <div class="sk">používateľské meno entity.</div>
     * @return <div class="en">Optional containing the entity or Optional.empty() if no entity was found.</div>
     * <div class="sk">Optional obsahujúci entitu alebo Optional.empty(), ak entita neexistuje.</div>
     */
    Optional<Employee> findEmployeeByUsername(String userName);

    /***
     * <div class="en">Finds the entity with the given id.</div>
     * <div class="sk">Nájde entitu so zadaným id.</div>
     * @param id <div class="en">id of entity.</div>
     *           <div class="sk">id entity.</div>
     * @return <div class="en">Optional containing the entity or Optional.empty()
     * if no entity was found.</div>
     * <div class="sk">Optional obsahujúci entitu alebo Mono.emtpy() ak entita neexistuje.</div>
     */
    Optional<Employee> getEmployeeById(String id);

    /***
     * <div class="en">Saves the entity.</div>
     * <div class="sk">Uloží entitu.</div>
     * @param employee <div class="en">entity to be saved.</div>
     *                 <div class="sk">entita, ktorá sa má uložiť.</div>
     * @return <div class="en">true if saved, false if not - employee already exists.</div>
     * <div class="sk">true ak entita bola uložená, false ak už entita s rovnakým požívateľským existovala.</div>
     */
    Employee saveEmployee(Employee employee);

    /***
     * <div class="en">Checks if the entity with the given user name exists.</div>
     * <div class="sk">Skontroluje, či entita so zadaným používateľským menom existuje.</div>
     * @param username <div class="en">user name of the entity.</div>
     *                 <div class="sk">používateľské meno entity.</div>
     * @return <div class="en">true if entity exists, false otherwise.</div>
     * <div class="sk">true, ak entita existuje, inak false.</div>
     */
    boolean existsByUsername(String username);
}
