package stuba.fei.gono.java.blocking.mongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import stuba.fei.gono.java.pojo.OrganisationUnit;

public interface OrganisationUnitRepository extends MongoRepository<OrganisationUnit,String> {
}
