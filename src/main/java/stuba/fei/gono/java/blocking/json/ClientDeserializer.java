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
 * Class that deserializes Client object from json based on it's id from id - it finds the instance of Client
 * in the database.
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
     * Deserializes the Client from JSON
     * @param jsonParser parser
     * @param deserializationContext parser context
     * @return deserialized Client from MongoDB
     * @throws IOException exception
     */
    @Override
    public Client deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return clientService.getClientById(jsonParser.getText()).orElse(null);
        //throw new ReportedOverlimitTransactionException("CLIENTID_NOT_VALID");
    }
}
