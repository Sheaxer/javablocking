package stuba.fei.gono.java.blocking.services;

import stuba.fei.gono.java.pojo.Client;

import java.util.Optional;
/***
 * Interface for marshalling and de-marshalling Client entities.
 */
public interface ClientService {
    /***
     * Finds the entity with the given id.
     * @param id id of entity.
     * @return Optional containing the entity or Optional.empty() if no entity was found.
     */
    Optional<Client> getClientById(String id);

}
