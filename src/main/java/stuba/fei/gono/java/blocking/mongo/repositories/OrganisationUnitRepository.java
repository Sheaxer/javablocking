package stuba.fei.gono.java.blocking.mongo.repositories;

import org.springframework.data.repository.CrudRepository;
import stuba.fei.gono.java.pojo.OrganisationUnit;
/***
 * <div class="en">Interface extending CrudRepository for OrganisationUnit.</div>
 * <div class="sk">Rozhranie rozširujúce CrudRepository pre entity tried OrganisationUnit.</div>
 * @see OrganisationUnit
 * @see CrudRepository
 */
public interface OrganisationUnitRepository extends CrudRepository<OrganisationUnit,String> {
}
