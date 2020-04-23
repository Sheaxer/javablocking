package stuba.fei.gono.java.blocking.services;

import stuba.fei.gono.java.pojo.OrganisationUnit;

import java.util.Optional;
/***
 * Interface for marshalling and de-marshalling OrganisationUnit entities.
 */
public interface OrganisationUnitService {
    /***
     * Finds the entity with the given id.
     * @param id id of the entity
     * @return Optional containing the entity or Optional.empty() if no entity was found.
     */
    Optional<OrganisationUnit> getOrganisationUnitById(String id);
}
