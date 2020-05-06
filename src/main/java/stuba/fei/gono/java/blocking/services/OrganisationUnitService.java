package stuba.fei.gono.java.blocking.services;

import stuba.fei.gono.java.pojo.OrganisationUnit;

import java.util.Optional;
/***
 * <div class="en">Interface for marshalling and de-marshalling OrganisationUnit entities.</div>
 * <div class="sk">Rozhranie pre marshalling a de-marshalling entít tried OrganisationUnit.</div>
 */
public interface OrganisationUnitService {
    /***
     * <div class="en">Finds the entity with the given id.</div>
     * <div class="sk">Nájde entitu so zadaným id</div>
     * @param id <div class="en">id of the entity.</div>
     *           <div class="sk">id entity.</div>
     * @return <div class="en">Optional containing the entity or Optional.empty()
     * if no entity was found.</div>
     * <div class="sk">Optional obsahujúci entitu alebo Optional.empty(), ak entita neexsistuje.</div>
     */
    Optional<OrganisationUnit> getOrganisationUnitById(String id);
}
