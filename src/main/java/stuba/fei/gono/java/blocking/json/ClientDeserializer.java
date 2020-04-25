package stuba.fei.gono.java.blocking.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import stuba.fei.gono.java.blocking.services.ClientService;
import stuba.fei.gono.java.pojo.Client;

import java.io.IOException;

/***
 * Class that deserializes Client object from json based on its id. Retrieves the entity with the id using
 * ClientService.
 * @see ClientService
 */
@Component
public class ClientDeserializer extends StdDeserializer<Client > {
    public ClientDeserializer(Class<?> vc) {
        super(vc);
    }
    public ClientDeserializer()
    {
        this(null);
    }
    @Autowired
    private ClientService clientService;

    /***
     * Deserializes the Client from JSON by returning its id.
     * @param jsonParser parser.
     * @param deserializationContext parser context.
     * @return value of id of Client.
     * @throws IOException exception.
     */
    @Override
    public Client deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return clientService.getClientById(jsonParser.getText()).orElse(null);
    }
}
