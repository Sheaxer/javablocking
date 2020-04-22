package stuba.fei.gono.java.blocking.services;

import org.springframework.stereotype.Service;
import stuba.fei.gono.java.pojo.Employee;

import java.util.Optional;


public interface EmployeeService {

    Optional<Employee> findEmloyeeByUsername(String userName);
    Optional<Employee> getEmployeeById(String id);

    boolean saveEmployee(Employee employee);

}
