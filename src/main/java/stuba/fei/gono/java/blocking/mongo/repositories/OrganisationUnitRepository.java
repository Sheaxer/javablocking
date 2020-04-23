package stuba.fei.gono.java.blocking.mongo.repositories;

import org.springframework.data.repository.CrudRepository;
import stuba.fei.gono.java.pojo.OrganisationUnit;
/***
 * Interface extending CrudRepository for OrganisationUnit.
 * @see OrganisationUnit
 * @see CrudRepository
 */
public interface OrganisationUnitRepository extends CrudRepository<OrganisationUnit,String> {
}
