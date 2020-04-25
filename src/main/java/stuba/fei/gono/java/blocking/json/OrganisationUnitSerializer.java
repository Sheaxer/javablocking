package stuba.fei.gono.java.blocking.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.stereotype.Component;
import stuba.fei.gono.java.pojo.OrganisationUnit;

import java.io.IOException;

/***
 *  Class that serializes OrganisationUnit by writing its id into the JsonGenerator.
 */
@Component
public class OrganisationUnitSerializer extends StdSerializer<OrganisationUnit> {
    protected OrganisationUnitSerializer(Class<OrganisationUnit> t) {
        super(t);
    }

    public OrganisationUnitSerializer()
    {
        this(null);
    }

    @Override
    public void serialize(OrganisationUnit organisationUnit,
                          JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(organisationUnit.getId());
    }
}
