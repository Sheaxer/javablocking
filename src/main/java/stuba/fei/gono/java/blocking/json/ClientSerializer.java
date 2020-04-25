package stuba.fei.gono.java.blocking.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.stereotype.Component;
import stuba.fei.gono.java.pojo.Client;

import java.io.IOException;

/***
 * Class to serialize Client by writing its id into JsonGenerator.
 */
@Component
public class ClientSerializer extends StdSerializer<Client> {
    protected ClientSerializer(Class<Client> t) {
        super(t);
    }

    public ClientSerializer()
    {
        this(null);
    }

    @Override
    public void serialize(Client client, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(client.getId());
    }
}
