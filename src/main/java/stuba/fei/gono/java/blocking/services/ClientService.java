package stuba.fei.gono.java.blocking.services;

import stuba.fei.gono.java.pojo.Client;

import java.util.Optional;
/***
 * <div class="en">Interface for marshalling and de-marshalling Client entities.</div>
 * <div class="sk">Rozhranie na marhalling a de-marshalling entít triedy Client.</div>
 */
public interface ClientService {
    /***
     * <div class="en">Finds the entity with the given id.</div>
     * <div class="sk">Nájde entitu so zadaným id.</div>
     * @param id <div class="en">id of entity.</div>
     *           <div class="sk">id entity.</div>
     * @return <div class="en">Optional containing the entity or Optional.empty() if no entity was found.
     * </div>
     * <div class="sk">Optional obsahujúci entitu alebo Optional.empty(), ek entita nebola nájdená.</div>
     */
    Optional<Client> getClientById(String id);

}
