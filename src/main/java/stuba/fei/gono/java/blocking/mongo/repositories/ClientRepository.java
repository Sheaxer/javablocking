package stuba.fei.gono.java.blocking.mongo.repositories;

import org.springframework.data.repository.CrudRepository;
import stuba.fei.gono.java.pojo.Client;

/***
 * Interface extending CrudRepository for Client.
 * @see Client
 * @see CrudRepository
 */
public interface ClientRepository extends CrudRepository<Client, String> {
}
