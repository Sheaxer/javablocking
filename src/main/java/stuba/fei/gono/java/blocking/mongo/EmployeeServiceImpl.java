package stuba.fei.gono.java.blocking.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import stuba.fei.gono.java.blocking.mongo.repositories.EmployeeRepository;
import stuba.fei.gono.java.pojo.Employee;
import stuba.fei.gono.java.services.EmployeeService;

import javax.validation.Valid;
import java.util.Optional;

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
    public Employee findEmloyeeByUsername(String userName) {
        return employeeRepository.findByUserName(userName).orElse(null);
    }

    @Override
    public boolean saveEmployee(@Valid Employee employee) {
        if(findEmloyeeByUsername(employee.getUserName()) != null)
            return false;
        employee.setId(nextSequenceService.getNewId(employeeRepository,sequenceName));
        //e.setUserName(userName);
        employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        employeeRepository.save(employee);
        return true;
    }
}
