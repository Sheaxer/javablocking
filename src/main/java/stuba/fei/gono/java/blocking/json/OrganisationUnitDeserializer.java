package stuba.fei.gono.java.blocking.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import stuba.fei.gono.java.blocking.services.OrganisationUnitService;
import stuba.fei.gono.java.pojo.OrganisationUnit;

import java.io.IOException;

/***
 * Class that deserializes OrganisationUnit object from json based on its id. Retrieves the entity with the id using
 * OrganisationUnitService.
 * @see OrganisationUnitService
 */
@Component
public class OrganisationUnitDeserializer extends StdDeserializer<OrganisationUnit> {

    @Autowired
    private OrganisationUnitService organisationUnitService;

    protected OrganisationUnitDeserializer(Class<?> vc) {
        super(vc);
    }

    public OrganisationUnitDeserializer()
    {
        this(null);
    }

    @Override
    public OrganisationUnit deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return organisationUnitService.getOrganisationUnitById(jsonParser.getText()).orElse(null);
    }
}
