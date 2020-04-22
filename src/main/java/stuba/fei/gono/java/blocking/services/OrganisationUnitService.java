package stuba.fei.gono.java.blocking.services;

import org.springframework.stereotype.Service;
import stuba.fei.gono.java.pojo.OrganisationUnit;

import java.util.Optional;

public interface OrganisationUnitService {

    Optional<OrganisationUnit> getOrganisationUnitById(String id);
}
