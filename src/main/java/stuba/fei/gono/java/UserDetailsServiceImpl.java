package stuba.fei.gono.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import stuba.fei.gono.java.pojo.Employee;
import stuba.fei.gono.java.blocking.services.EmployeeService;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private EmployeeService employeeService;

    @Autowired
    public UserDetailsServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Employee employee= employeeService.findEmloyeeByUsername(s).orElseThrow(() ->
                new UsernameNotFoundException("USERNAME_NOTFOUND"));
        /*if(employee == null)
                throw new UsernameNotFoundException("USERNAME_NOTFOUND");*/
        return new User(employee.getUsername(),employee.getPassword(), Collections.emptyList());
    }
}
