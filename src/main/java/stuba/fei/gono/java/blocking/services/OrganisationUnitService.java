package stuba.fei.gono.java.blocking.services;

import org.springframework.stereotype.Service;
import stuba.fei.gono.java.pojo.OrganisationUnit;
@Service
public interface OrganisationUnitService {

    OrganisationUnit getOrganisationUnitById(String id);
}
