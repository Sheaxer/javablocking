package stuba.fei.gono.java.blocking.mongo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stuba.fei.gono.java.blocking.mongo.repositories.OrganisationUnitRepository;
import stuba.fei.gono.java.blocking.services.OrganisationUnitService;
import stuba.fei.gono.java.pojo.OrganisationUnit;
@Service
public class OrganisationUnitServiceImpl implements OrganisationUnitService {

    private OrganisationUnitRepository organisationUnitRepository;

    @Autowired
    public OrganisationUnitServiceImpl(OrganisationUnitRepository organisationUnitRepository) {
        this.organisationUnitRepository = organisationUnitRepository;
    }

    @Override
    public OrganisationUnit getOrganisationUnitById(String id) {
        return this.organisationUnitRepository.findById(id).orElse(null);
    }
}
