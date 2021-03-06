package stuba.fei.gono.java.blocking.mongo.data.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import stuba.fei.gono.java.blocking.mongo.data.repositories.OrganisationUnitRepository;
import stuba.fei.gono.java.pojo.OrganisationUnit;
@Component
public class OrganisationUnitConverter implements Converter<String, OrganisationUnit> {

    private final OrganisationUnitRepository repository;

    @Autowired
    public OrganisationUnitConverter(OrganisationUnitRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public OrganisationUnit convert(String s) {
        return repository.findById(s).orElse(null);
    }
}
