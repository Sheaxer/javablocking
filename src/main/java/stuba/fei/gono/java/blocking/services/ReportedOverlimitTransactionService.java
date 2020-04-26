package stuba.fei.gono.java.blocking.services;


import stuba.fei.gono.java.blocking.pojo.ReportedOverlimitTransaction;
import stuba.fei.gono.java.errors.ReportedOverlimitTransactionBadRequestException;
import stuba.fei.gono.java.errors.ReportedOverlimitTransactionNotFoundException;

import java.util.Optional;

/***
 * Interface for marshalling and de-marshalling ReportedOverlimitTransaction  entities.
 */
public interface ReportedOverlimitTransactionService {
     /***
      * Generates new id and saves the entity.
      * @param transaction entity to be saved
      * @return saved entity.
      */
     ReportedOverlimitTransaction postTransaction(ReportedOverlimitTransaction transaction);

     /***
      * Retrieves the entity with the given id.
      * @param id id of entity.
      * @return Optional containing the entity or Optional.empty() if no entity was found.
      */
     Optional<ReportedOverlimitTransaction> getTransactionById(String id);

     /***
      * Saves the entity with the given id.
      * @param id id which will identify the saved entity.
      * @param transaction entity to be saved.
      * @return saved entity.
      */
     ReportedOverlimitTransaction putTransaction(String id, ReportedOverlimitTransaction transaction);

     /***
      * Deletes the entity identified by the given id.
      * @param id id of entity.
      * @return true if the entity was deleted, false it there was no entity to be deleted.
      * @throws ReportedOverlimitTransactionBadRequestException when the entity couldn't be deleted.
      */
     boolean deleteTransaction (String id) throws ReportedOverlimitTransactionBadRequestException;

}
