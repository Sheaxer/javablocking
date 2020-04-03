package stuba.fei.gono.java.blocking.mongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import stuba.fei.gono.java.pojo.Employee;

import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Optional<Employee> findByUserName(String userName);

}
