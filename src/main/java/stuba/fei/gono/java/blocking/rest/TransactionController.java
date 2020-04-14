package stuba.fei.gono.java.blocking.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import stuba.fei.gono.java.errors.ReportedOverlimitTransactionException;
import stuba.fei.gono.java.blocking.pojo.ReportedOverlimitTransaction;
import stuba.fei.gono.java.blocking.services.ReportedOverlimitTransactionService;
import stuba.fei.gono.java.pojo.State;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/reportedOverlimitTransaction")
//@CrossOrigin(origins = "http://a.com")
@Slf4j
public class TransactionController {

    private ReportedOverlimitTransactionService transactionService;

    /*@Autowired
    public TransactionController(ReportedOverlimitTransactionRepository transactionRepository, NextSequenceService nextSequenceService)
    {
        //this.clientRepository = clientRepository;
        this.transactionRepository = transactionRepository;
        this.nextSequenceService = nextSequenceService;
    }*/
    @Autowired
    public TransactionController(ReportedOverlimitTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /***
     * Returns single ReportedOverlimitTransaction based on its  id.
     * @see ReportedOverlimitTransaction
     * @param id Id of the requested ReportedOverlimitTransaction
     * @return requested instance of ReportedOverlimitTransaction
     * @throws ReportedOverlimitTransactionException exception if there is no instance of ReportedOverlimitTransaction with the requested id
     */
    @GetMapping(value = "/{id}",produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ReportedOverlimitTransaction getTransaction(@PathVariable String id)
    {
        return transactionService.getTransactionById(id);
    }

    /*@GetMapping(value = "/{id}")
    public ResponseEntity<ReportedOverlimitTransaction> getTransaction(@PathVariable String id) throws ReportedOverlimitTransactionException
    {
        Optional<ReportedOverlimitTransaction> transaction= transactionRepository.findById(id);
        if(transaction.isPresent())
        {
            ReportedOverlimitTransaction trans = transaction.get();
            return new ResponseEntity<>(trans,HttpStatus.OK);
        }
        else
        {
           throw new ReportedOverlimitTransactionException("ID_NOT_FOUND");
        }
    }*/

    @PostMapping(produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ReportedOverlimitTransaction postTransaction(@Valid @RequestBody ReportedOverlimitTransaction newTransaction)
    {
        return transactionService.postTransaction(newTransaction);
    }

    /*@PostMapping
    public ResponseEntity<ReportedOverlimitTransaction> postTransaction(@Valid @RequestBody ReportedOverlimitTransaction newTransaction)
            throws CreateReportedOverlimitTransactionException
    {

        log.info(this.sequenceName);
        String newId = nextSequenceService.getNewId(transactionRepository,sequenceName);

        newTransaction.setId(newId);


        if(newTransaction.getState() == null)
            newTransaction.setState(State.CREATED);
        newTransaction.setModificationDate(OffsetDateTime.now());
        newTransaction.setZoneOffset(newTransaction.getModificationDate().getOffset().getId());
        transactionRepository.save(newTransaction);
        log.info("Created new transaction " + newTransaction.getId());
        return new ResponseEntity<>(newTransaction,HttpStatus.CREATED);
    }*/

    /***
     * Deletes non-closed ReportedOverlimitTransaction with the request id from database.
     * @see ReportedOverlimitTransaction
     * @see State
     * @param id id of ReportedOverlimitTransaction that should be deleted
     * @throws ReportedOverlimitTransactionException if requested ReportedOverlimitTransaction cannot be deleted
     * either because it's state is not CLOSED or there is isn't one with requested it stored.
     */
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransaction(@PathVariable String id)
    {
       transactionService.deleteTransaction(id);

    }
    /*@DeleteMapping(value = "/{id}")
    public ResponseEntity<ReportedOverlimitTransaction> deleteTransaction(@PathVariable String id)
    {
        Optional<ReportedOverlimitTransaction> transaction= transactionRepository.findById(id);
        if(transaction.isPresent())
        {
            ReportedOverlimitTransaction trans = transaction.get();
            if(!trans.getState().equals(State.CLOSED))
            {
                transactionRepository.delete(trans);
                return new ResponseEntity<>(trans,HttpStatus.OK);
            }
            else
                throw new ReportedOverlimitTransactionException("STATE_CLOSED");

        }
        else
        {
            throw new ReportedOverlimitTransactionException("ID_NOT_FOUND");
        }
    }*/

    @PutMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ReportedOverlimitTransaction putTransaction(@PathVariable String id, @Valid @RequestBody ReportedOverlimitTransaction transaction)
    {
       return transactionService.putTransaction(id,transaction);

    }

    @PostMapping(value="/{id}", produces = "application/json")
    public ReportedOverlimitTransaction postTransaction(@PathVariable String id, @Valid @RequestBody ReportedOverlimitTransaction transaction)
    {
        return putTransaction(id,transaction);
    }

    /*@PutMapping(value = "/{id}")
    @PostMapping(value="/{id}")
    public ResponseEntity<ReportedOverlimitTransaction> putTransaction(@PathVariable String id, @Valid @RequestBody ReportedOverlimitTransaction transaction)
    {
        //Optional<ReportedOverlimitTransaction> tran= transactionRepository.findById(id);
        transaction.setId(id);
        transaction.setModificationDate(OffsetDateTime.now());
        transaction.setZoneOffset(transaction.getModificationDate().getOffset().getId());
        transactionRepository.save(transaction);
        return new ResponseEntity<>(transaction, HttpStatus.OK );
    }*/

}
