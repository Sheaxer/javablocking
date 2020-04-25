package stuba.fei.gono.java.blocking.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.stereotype.Component;
import stuba.fei.gono.java.pojo.Employee;

import java.io.IOException;

/***
 * Class that serializes Employee by writing its id into the JsonGenerator.
 */
@Component
public class EmployeeSerializer extends StdSerializer<Employee> {
    protected EmployeeSerializer(Class<Employee> t) {
        super(t);
    }

    public EmployeeSerializer()
    {
        this(null);
    }


    @Override
    public void serialize(Employee employee, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(employee.getId());
    }
}
