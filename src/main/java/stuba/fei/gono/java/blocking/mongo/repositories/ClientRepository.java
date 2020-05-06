package stuba.fei.gono.java.blocking.mongo.repositories;

import org.springframework.data.repository.CrudRepository;
import stuba.fei.gono.java.pojo.Client;

/***
 * <div class="en">Interface extending CrudRepository for Client.</div>
 * <div class="sk">Rozrhranie rozširujúce CrudRepository pre objekty triedy Client.</div>
 * @see Client
 * @see CrudRepository
 */
public interface ClientRepository extends CrudRepository<Client, String> {
}
