package stuba.fei.gono.java.services;

import stuba.fei.gono.java.pojo.Employee;

import java.util.Optional;


public interface EmployeeService {

    Optional<Employee> findEmloyeeByUsername(String userName);

    boolean saveEmployee(Employee employee);

}
