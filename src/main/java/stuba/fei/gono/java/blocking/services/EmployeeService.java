package stuba.fei.gono.java.blocking.services;

import stuba.fei.gono.java.pojo.Employee;


public interface EmployeeService {

    Employee findEmloyeeByUsername(String userName);

    boolean saveEmployee(Employee employee);

}
