package stuba.fei.gono.java.blocking.mongo.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import stuba.fei.gono.java.pojo.OrganisationUnit;

public interface OrganisationUnitRepository extends MongoRepository<OrganisationUnit,String> {
}
