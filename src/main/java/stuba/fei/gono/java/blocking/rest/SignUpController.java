package stuba.fei.gono.java.blocking.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import stuba.fei.gono.java.blocking.services.EmployeeService;
import stuba.fei.gono.java.errors.ReportedOverlimitTransactionBadRequestException;
import stuba.fei.gono.java.pojo.Employee;

import javax.validation.Valid;

/***
 * <div class="en">Rest Controller allowing POST method to endpoint /signup to register a new employee.</div>
 * <div class="sk">Rest kontrolér poskytujúci POST metódou na /signup endpoint-e, ktorá umožnuje
 * pridanie nového zamestnanca.</div>
 */
@RestController
public class SignUpController {

    private EmployeeService employeeService;

    @Autowired
    public SignUpController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping(value = "/signup", consumes = "application/json")
    @ResponseBody
    public String signUp(@RequestBody @Valid Employee user) {
        if( employeeService.saveEmployee(user))
            return "SUCCESSFULLY_REGISTERED";
        else
            throw new ReportedOverlimitTransactionBadRequestException( "USERNAME_ALREADY_EXISTS");

    }
}
