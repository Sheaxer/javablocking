package stuba.fei.gono.java.blocking.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import stuba.fei.gono.java.pojo.Employee;
import stuba.fei.gono.java.blocking.services.EmployeeService;

import java.util.Collections;

/***
 * <div class="en">Implementation of UserDetailsService for logging into system. Checks
 * if the log in information identify an Employee entity.</div>
 * <div class="sk">Implementácia rozhrania UserDetailsService pre prihlásenie do systému.
 * Skontroluje, či prihlasovacie údaje identifikujú entitu triedy Employee.</div>
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private EmployeeService employeeService;

    @Autowired
    public UserDetailsServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /***
     * <div class="en">Finds Employee entity with the given user name via EmployeeService instance.</div>
     * <div class="sk">Nájde entitu triedy Employee so zadaným menom pomocou inštancie EmployeeService.</div>
     * @param s <div class="en">user name.</div>
     *          <div class="sk">používateľské meno.</div>
     * @return <div class="en">instance of User with user name and password retrieved from Employee entity.</div>
     * <div class="sk">inštancia triedy User s používateľským menom a heslom získaným z entity triedy Employee.</div>
     * @throws UsernameNotFoundException <div class="en">thrown if there is no Employee entity with the given user name.
     * </div>
     * <div class="sk">výnimka vyvolaná, ak neexistuje entita triedy Employee so zadaným používateľským menom.</div>
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Employee employee= employeeService.findEmployeeByUsername(s).orElseThrow(() ->
                new UsernameNotFoundException("USERNAME_NOTFOUND"));
        return new User(employee.getUsername(),employee.getPassword(), Collections.emptyList());
    }
}
