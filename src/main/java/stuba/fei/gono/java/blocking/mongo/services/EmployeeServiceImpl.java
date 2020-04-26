package stuba.fei.gono.java.blocking.mongo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import stuba.fei.gono.java.blocking.mongo.repositories.EmployeeRepository;
import stuba.fei.gono.java.pojo.Employee;
import stuba.fei.gono.java.blocking.services.EmployeeService;

import javax.validation.Valid;
import java.util.Optional;

/***
 * Implementation of EmployeeService for use with MongoDB.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private NextSequenceService nextSequenceService;
    @Value("${reportedOverlimitTransaction.employee.sequenceName:employeeSequence}")
    private String sequenceName;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, NextSequenceService nextSequenceService,
                               BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.employeeRepository = employeeRepository;
        this.nextSequenceService = nextSequenceService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Optional<Employee> findEmloyeeByUsername(String userName) {
        return employeeRepository.findEmployeeByUsername(userName);
    }

    @Override
    public Optional<Employee> getEmployeeById(String id) {
        return employeeRepository.findById(id);
    }

    /***
     *  Hashes password of employee using BCrypt and saves the entity into the repository.
     * @param employee entity to be saved.
     * @return true if entity was saved, false if entity with the same username is already present in the repository.
     */
    @Override
    public boolean saveEmployee(@Valid Employee employee) {
        if(findEmloyeeByUsername(employee.getUsername()).isPresent())
            return false;
        employee.setId(nextSequenceService.getNewId(employeeRepository,sequenceName));
        //e.setUserName(userName);
        employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        employeeRepository.save(employee);
        return true;
    }
}
