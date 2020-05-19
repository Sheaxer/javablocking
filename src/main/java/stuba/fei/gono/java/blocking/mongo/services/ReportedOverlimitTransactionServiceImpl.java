package stuba.fei.gono.java.blocking.mongo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import stuba.fei.gono.java.blocking.mongo.repositories.ReportedOverlimitTransactionRepository;
import stuba.fei.gono.java.blocking.pojo.ReportedOverlimitTransaction;
import stuba.fei.gono.java.blocking.services.ReportedOverlimitTransactionService;
import stuba.fei.gono.java.errors.ReportedOverlimitTransactionBadRequestException;
import stuba.fei.gono.java.pojo.State;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Optional;

/***
 * <div class="en">Implementation of ReportedOverlimitTransactionService using CRUD operations
 * and auto generated instance of ReportedOverlimitTransactionRepository. Uses NextSequenceService for
 * generation of ids used in saving new entities.</div>
 * <div class="sk">Implementácia rozhrania ReportedOverlimitTransactionService pomocou
 * CRUD operácií a automaticky generovanej inštancie ReportedOverlimitTransactionRepository.
 * Využíva inštanciu NextSequenceService na generáciu id pre ukladanie entít.</div>
 */
@Service
public class ReportedOverlimitTransactionServiceImpl implements ReportedOverlimitTransactionService {
    /***
     * <div class="en">Name of the sequence containing maximal value of id
     * that was used to save entity in the repository.</div>
     * <div class="sk">Názov sekvencie obsahujúcej maximálnu hodnotu id použitého na
     * uloženie entity do repozitára.</div>
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
     * <div class="en">Generates new id using NextSequenceService and saved entity with this id in the repository.
     * Sets the modification date of entity to the time of saving and sets the state to State.CREATED.</div>
     * <div class="sk">Generuje nové id pomocou inštancie NextSequenceService a uloží entitu do repozitára
     * s týmto id. Nastaví dátum modifikácie entity na čas uloženia a nastaví stav na State.CREATED.</div>
     * @see NextSequenceService
     * @see State
     * @param newTransaction <div class="en">entity to be saved.</div>
     *                       <div class="sk">entita, ktorá sa má uložiť.</div>
     * @return <div class="en">saved entity.</div>
     * <div class="sk">uložená entita.</div>
     */
    @Override
    public ReportedOverlimitTransaction postTransaction(@NotNull ReportedOverlimitTransaction newTransaction) {

        String newId = nextSequenceService.getNewId(transactionRepository,sequenceName);
        newTransaction.setState(State.CREATED);
        newTransaction.setModificationDate(OffsetDateTime.now());
        return putTransaction(newId,newTransaction);
    }


    @Override
    public Optional<ReportedOverlimitTransaction> getTransactionById(@NotNull String id)  {
        return transactionRepository.findById(id);
    }

    /***
     * <div class="en">Saves the entity using the given id in the repository. Sets modification date to time of saving,
     * and if there was no entity with the given id before sets the state to CREATED.
     * Notifies the NextSequenceService to check if the id given is a new maximal value.</div>
     * <div class="sk">Uloží entitu so zadaným id do repozitára. Nastaví dátum modifikácie na čas
     * ukladania a ak neexistovala entita so zadaným id nastaví jej stav na State.CREATED</div>
     * @see State
     * @param id id which will identify the saved entity.
     * @param transaction entity to be saved.
     * @return saved entity.
     */
    @Override
    public ReportedOverlimitTransaction putTransaction(@NotNull String id,
                                                       @NotNull ReportedOverlimitTransaction transaction) {
        transaction.setId(id);
        transaction.setModificationDate(OffsetDateTime.now());
        if(!transactionRepository.existsById(id))
            transaction.setState(State.CREATED);
        nextSequenceService.needsUpdate(sequenceName,id);
        if(transaction.getState() == null)
            transaction.setState(State.CREATED);
        transactionRepository.save(transaction);
        return transaction;
    }

    /***
     * <div class="en">Deletes the entity with the given id if it exists in the repository and its state is not
     * State.CLOSED.</div>
     * <div class="sk">Zmaže entitu so zadaným id ak táto entita existovala a jej stav nie je State.CLOSED.</div>
     * @see State
     * @param id <div class="en">id of entity.</div>
     *           <div class="sk">id entity.</div>
     * @return <div class="en">true if entity with given id was found, its state was not CLOSED and it was deleted.
     * </div>
     * <div class="sk">true ak entita existovala, jej stav nebol State.CLOSED a bola zmazaná, false ak entita
     * neexistovala.</div>
     * @throws ReportedOverlimitTransactionBadRequestException <div class="en">in case the entity couldn't
     * be deleted because its state was State.CLOSED.</div>
     * <div class="sk">ak entita nemohla byť zmazaná, pretože jej stav bol State.CLOSED</div>
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
