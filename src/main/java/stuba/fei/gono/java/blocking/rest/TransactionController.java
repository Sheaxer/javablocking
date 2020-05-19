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
 * <div class="en">REST controller for GET,POST,PUT and DELETE methods for ReportedOverlimitTransaction entities
 * on /reportedOverlimitTransaction.</div>
 * <div class="sk">REST kontrolér ktorý poskytuje prístup k zdroju ReportedOverlimitTransaction entitám cez metódy
 * GET, PUT, POST a DELETE na /reportedOverlimitTransaction endpointe.</div>
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
     * <div class="en">Returns single ReportedOverlimitTransaction based on its id.</div>
     * <div class="sk">Vráti entitu triedy ReportedOverlimitTransaction podľa zadaného id.</div>
     * @see ReportedOverlimitTransaction
     * @param id <div class="en">idd of the requested ReportedOverlimitTransaction.</div>
     *           <div class="sk">id hľadanej entity.</div>
     * @return <div class="en">requested instance of ReportedOverlimitTransaction.</div>
     * <div class="sk">hľadaná inštancia triedy ReportedOverlimitTransaction.</div>
     * @throws ReportedOverlimitTransactionNotFoundException <div class="en">
     *     exception thrown if there is no instance entity with the given id.</div>
     *     <div class="sk">výnimka vyvolaná, ak neexistuje entita so zadaným id.</div>
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
     * <div class="en">POST method - generates new id and saves the entity with the new id.</div>
     * <div class="sk">POST metóda - generuje nové id a uloží entitu s týmto id.</div>
     * @param newTransaction <div class="en">entity to be saved.</div><div class="sk">entita, ktorá sa má uložiť.</div>
     * @return <div class="en">saved entity.</div><div class="sk">uložená entita.</div>
     */
    @PostMapping(produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ReportedOverlimitTransaction postTransaction(@Valid @RequestBody ReportedOverlimitTransaction newTransaction)
    {
        return transactionService.postTransaction(newTransaction);
    }

    /***
     * <div class="en">Deletes non-closed ReportedOverlimitTransaction with the request id from database.</div>
     * <div class="sk">Zmaže entitu so zadaným id z databázy, ak nemá stav CLOSED.</div>
     * @see ReportedOverlimitTransaction
     * @see State
     * @param id <div class="en">id of the entity that should be deleted.</div>
     *           <div class="sk">id entity, ktorá sa má zmazať.</div>
     * @throws ReportedOverlimitTransactionNotFoundException <div class="en">exception thrown if there is no entity
     * with the given id.</div> <div class="sk">výnimka vyvolaná, ak entita so zadaným id neexistuje.</div>
     * @throws ReportedOverlimitTransactionBadRequestException <div class="en">exception thrown if the entity
     * with given id cannot be deleted because its state is CLOSED.</div>
     * <div class="sk">výnimka vyvolaná, ak entita so zadaným id nemôže byť vymazaná, pretože jej stav je CLOSED.</div>
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
     * <div class="en">PUT method - saves the entity with the given id (if an entity with the given id
     * existed before it will be overwritten). </div>
     * <div class="sk">PUT metóda - uloží entitu pod zadaným id (ak už entita so zaaaným id existovala, prepíše ju).
     * </div>
     * @param id <div class="en">id that will represent the saved entity.</div>
     *           <div class="sk">id ktoré bude reprezentovať uloženú entitu.</div>
     * @param transaction <div class="en">entity to be saved.</div>
     *                    <div class="sk">entita ktorá má byť uložená.</div>
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
