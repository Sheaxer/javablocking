package stuba.fei.gono.java.blocking.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import stuba.fei.gono.java.blocking.pojo.ReportedOverlimitTransaction;
import stuba.fei.gono.java.blocking.services.ReportedOverlimitTransactionService;
import stuba.fei.gono.java.errors.ReportedOverlimitTransactionBadRequestException;
import stuba.fei.gono.java.errors.ReportedOverlimitTransactionNotFoundException;
import stuba.fei.gono.java.pojo.State;

import javax.validation.Valid;

/***
 * REST controller for GET,POST,PUT and DELETE methods for ReportedOverlimitTransaction entities.
 * @see ReportedOverlimitTransaction
 */
@RestController
@RequestMapping(value = "/reportedOverlimitTransaction")
@Slf4j
public class TransactionController {

    private ReportedOverlimitTransactionService transactionService;

    @Autowired
    public TransactionController(ReportedOverlimitTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /***
     * Returns single ReportedOverlimitTransaction based on its  id.
     * @see ReportedOverlimitTransaction
     * @param id Id of the requested ReportedOverlimitTransaction
     * @return requested instance of ReportedOverlimitTransaction
     * @throws ReportedOverlimitTransactionNotFoundException exception if there is no instance entity with the given id.
     */
    @GetMapping(value = "/{id}",produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ReportedOverlimitTransaction getTransaction(@PathVariable String id)
    {
        return transactionService.getTransactionById(id).orElseThrow(() ->
                new ReportedOverlimitTransactionNotFoundException("ID_NOT_FOUND"));
    }

    /***
     * POST method - generates new id and saves the entity with the new id.
     * @param newTransaction entity to be saved
     * @return saved entity.
     */
    @PostMapping(produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ReportedOverlimitTransaction postTransaction(@Valid @RequestBody ReportedOverlimitTransaction newTransaction)
    {
        return transactionService.postTransaction(newTransaction);
    }

    /***
     * Deletes non-closed ReportedOverlimitTransaction with the request id from database.
     * @see ReportedOverlimitTransaction
     * @see State
     * @param id id of ReportedOverlimitTransaction that should be deleted
     * @throws ReportedOverlimitTransactionNotFoundException if there is no entity with the given id.
     * @throws ReportedOverlimitTransactionBadRequestException if the entity with given id cannot be deleted because its
     * state is CLOSED.
     */
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransaction(@PathVariable String id) throws ReportedOverlimitTransactionBadRequestException,
            ReportedOverlimitTransactionNotFoundException
    {
       if(!transactionService.deleteTransaction(id))
           throw new ReportedOverlimitTransactionNotFoundException("ID_NOT_FOUND");

    }

    /***
     * PUT method - saves the entity with the given id.
     * @param id id that will represent the saved entity.
     * @param transaction entity to be saved.
     * @return saved entity.
     */
    @PutMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ReportedOverlimitTransaction putTransaction(@PathVariable String id,
                                                       @Valid @RequestBody ReportedOverlimitTransaction transaction)
    {
       return transactionService.putTransaction(id,transaction);

    }

    /***
     * POST method - saves the entity with the given id.
     * @param id id that will represent the saved entity.
     * @param transaction entity to be saved.
     * @return saved entity.
     */
    @PostMapping(value="/{id}", produces = "application/json")
    public ReportedOverlimitTransaction postTransaction(@PathVariable String id,
                                                        @Valid @RequestBody ReportedOverlimitTransaction transaction)
    {
        return putTransaction(id,transaction);
    }
}
