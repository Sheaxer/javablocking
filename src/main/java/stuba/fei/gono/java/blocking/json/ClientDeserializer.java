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
 * <div class="en">Class that deserializes Client object from json based on its id. Retrieves the entity with the
 * id retireved from JsonParser using ClientService .</div>
 * <div class="sk">Trieda na de-serializáciu objektov triedy Client na základe id. Využije
 * CLientService na nájdenie entity s id získaným z JsonParser.</div>
 * @see ClientService
 * @see JsonParser
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


    @Override
    public Client deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return clientService.getClientById(jsonParser.getText()).orElse(null);
    }
}
