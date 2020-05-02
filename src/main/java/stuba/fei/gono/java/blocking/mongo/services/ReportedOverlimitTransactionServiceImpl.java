package stuba.fei.gono.java.blocking.mongo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import stuba.fei.gono.java.errors.ReportedOverlimitTransactionBadRequestException;
import stuba.fei.gono.java.errors.ReportedOverlimitTransactionNotFoundException;
import stuba.fei.gono.java.blocking.mongo.repositories.ReportedOverlimitTransactionRepository;
import stuba.fei.gono.java.blocking.pojo.ReportedOverlimitTransaction;
import stuba.fei.gono.java.pojo.State;
import stuba.fei.gono.java.blocking.services.ReportedOverlimitTransactionService;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Optional;

/***
 * Implementation of ReportedOverlimitTransactionService for use with MongoDB.
 */
@Service
public class ReportedOverlimitTransactionServiceImpl implements ReportedOverlimitTransactionService {
    /***
     * Name of the sequence containing maximal value of id that was used to save entity in the repository.
     */
    @Value("${reportedOverlimitTransaction.transaction.sequenceName:customSequences}")
    private String sequenceName;
    private ReportedOverlimitTransactionRepository transactionRepository;
    private NextSequenceService nextSequenceService;

    @Autowired
    public ReportedOverlimitTransactionServiceImpl(ReportedOverlimitTransactionRepository transactionRepository,
                                                   NextSequenceService nextSequenceService)
    {
        this.transactionRepository = transactionRepository;
        this.nextSequenceService = nextSequenceService;
    }

    /***
     * Generates new id using NextSequenceService and saved entity with this id in the repository. Sets the
     * modification date of entity to the time of saving and sets the state to CREATED.
     * @see NextSequenceService
     * @see State
     * @param newTransaction entity to be saved.
     * @return saved entity.
     */
    @Override
    public ReportedOverlimitTransaction postTransaction(@NotNull ReportedOverlimitTransaction newTransaction) {

        String newId = nextSequenceService.getNewId(transactionRepository,sequenceName);
        newTransaction.setState(State.CREATED);
        newTransaction.setModificationDate(OffsetDateTime.now());
        return putTransaction(newId,newTransaction);
    }

    /***
     * Finds entity with the given id in the repository.
     * @param id id of entity.
     * @return Optional containing the entity or Optional.empty() if none found.
     */
    @Override
    public Optional<ReportedOverlimitTransaction> getTransactionById(@NotNull String id)  {
        return transactionRepository.findById(id);
    }

    /***
     * Saves the entity using the given id in the repository. Sets modification date to time of saving, and if
     * there was no entity with the given id before sets the state to CREATED. Notifies the NextSequenceService to
     * check if the id given is a new maximal value.
     * @param id id which will identify the saved entity.
     * @param transaction entity to be saved.
     * @return saved entity.
     */
    @Override
    public ReportedOverlimitTransaction putTransaction(@NotNull String id,
                                                       @NotNull ReportedOverlimitTransaction transaction) {
        transaction.setId(id);
        transaction.setModificationDate(OffsetDateTime.now());
        if(transactionRepository.existsById(id))
            transaction.setState(State.CREATED);
        nextSequenceService.needsUpdate(sequenceName,id);
        transactionRepository.save(transaction);
        return transaction;
    }

    /***
     * Deletes the entity with the given id if it exists in the repository and its state is not CLOSED.
     * @param id id of entity.
     * @return true if entity with given id was found, its state was not CLOSED and it was deleted.
     * @throws ReportedOverlimitTransactionBadRequestException in case the entity couldn't be deleted because its
     * state was CLOSED.
     */
    @Override
    public boolean deleteTransaction(@NotNull String id)
    throws ReportedOverlimitTransactionBadRequestException
    {
        Optional<ReportedOverlimitTransaction> transaction= transactionRepository.findById(id);
        if(transaction.isPresent())
        {
            ReportedOverlimitTransaction trans = transaction.get();
            if(!trans.getState().equals(State.CLOSED))
            {
                transactionRepository.delete(trans);
                return  true;
            }
            else
                throw new ReportedOverlimitTransactionBadRequestException("STATE_CLOSED");

        }
        else
        {
           return false;
        }
    }
}
