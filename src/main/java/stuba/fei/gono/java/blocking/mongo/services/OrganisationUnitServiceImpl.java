package stuba.fei.gono.java.blocking.mongo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stuba.fei.gono.java.blocking.mongo.repositories.OrganisationUnitRepository;
import stuba.fei.gono.java.blocking.services.OrganisationUnitService;
import stuba.fei.gono.java.pojo.OrganisationUnit;

import java.util.Optional;

/***
 * <div class="en">Implementation of OrganisationUnitService using CRUD operations and auto generated instance
 * of OrganisationUnitRepository.</div>
 * <div class="sk">Implementácia rozhrania OrganisationUnitService pomocou CRUD operácií a
 * automaticky generovanej inštancie rozhrania OrganisationUnitRepository</div>
 * @see OrganisationUnitRepository
 */
@Service
public class OrganisationUnitServiceImpl implements OrganisationUnitService {

    private OrganisationUnitRepository organisationUnitRepository;

    @Autowired
    public OrganisationUnitServiceImpl(OrganisationUnitRepository organisationUnitRepository) {
        this.organisationUnitRepository = organisationUnitRepository;
    }

    @Override
    public Optional<OrganisationUnit> getOrganisationUnitById(String id) {
        return this.organisationUnitRepository.findById(id);
    }
}
