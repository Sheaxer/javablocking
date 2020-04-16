package stuba.fei.gono.java.blocking.services;

import org.springframework.stereotype.Service;
import stuba.fei.gono.java.pojo.Employee;

@Service
public interface EmployeeService {

    Employee findEmloyeeByUsername(String userName);
    Employee getEmployeeById(String id);

    boolean saveEmployee(Employee employee);

}
