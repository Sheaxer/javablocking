package stuba.fei.gono.java.blocking.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import stuba.fei.gono.java.pojo.Employee;
import stuba.fei.gono.java.services.EmployeeService;

import javax.validation.Valid;

@RestController
public class SignUpController {

    private EmployeeService employeeService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SignUpController(EmployeeService employeeService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.employeeService = employeeService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/signup")
    @ResponseBody
    public String signUp(@RequestBody @Valid Employee user) {
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return employeeService.saveEmployee(user) ? "successfully_registered" : "USERNAME_ALREADY_EXISTS";

    }
}
