package stuba.fei.gono.java.blocking.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.stereotype.Component;
import stuba.fei.gono.java.pojo.OrganisationUnit;

import java.io.IOException;

/***
 *  <div class="en">Class that serializes OrganisationUnit by writing its id into the JsonGenerator.</div>
 *  <div class="sk">Trieda, ktorá serializuje objekty triedy OrganisationUnit do Json reťazca zapísaním
 *  id objektu do JsonGenerator-a.</div>
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
