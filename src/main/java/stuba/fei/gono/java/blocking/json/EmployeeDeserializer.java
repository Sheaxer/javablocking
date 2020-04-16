package stuba.fei.gono.java.blocking.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import stuba.fei.gono.java.blocking.services.EmployeeService;
import stuba.fei.gono.java.pojo.Employee;

import java.io.IOException;

@Component
public class EmployeeDeserializer extends StdDeserializer<Employee> {
    public EmployeeDeserializer(Class<?> vc) {
        super(vc);
    }

    public EmployeeDeserializer() {
        this(null);
    }

    @Autowired
    private EmployeeService employeeService;

    @Override
    public Employee deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return employeeService.getEmployeeById(jsonParser.getText());
    }
}
