package stuba.fei.gono.java.blocking.services;


import stuba.fei.gono.java.blocking.pojo.ReportedOverlimitTransaction;
import stuba.fei.gono.java.errors.ReportedOverlimitTransactionBadRequestException;
import stuba.fei.gono.java.errors.ReportedOverlimitTransactionNotFoundException;

import java.util.Optional;

/***
 * <div class="en">Interface for marshalling and de-marshalling ReportedOverlimitTransaction entities.</div>
 * <div class="sk">Rozhranie na marshalling a de-marshalling entít triedy ReportedOverlimitTransaction</div>
 */
public interface ReportedOverlimitTransactionService {
     /***
      * <div class="id">Generates new id and saves the entity.</div>
      * <div class="sk">Generuje nové id a uloží entitu s týmto id.</div>
      * @param transaction <div class="en">entity to be saved.</div>
      *                    <div class="sk">entita, ktorá sa má uložiť.</div>
      * @return <div class="en">saved entity.</div>
      * <div class="sk">uložená entita.</div>
      */
     ReportedOverlimitTransaction postTransaction(ReportedOverlimitTransaction transaction);

     /***
      * <div class="en">Finds the entity with the given id.</div>
      * <div class="sk">Nájde entitu so zadaným id.</div>
      * @param id <div class="en">id of entity.</div>
      *           <div class="sk">id entity.</div>
      * @return <div class="en">Optional containing the entity or Optional.empty()
      * if no entity was found.</div>
      * <div class="sk">Optional obsahujúci entitu alebo Optional.empty(), ak entita nebola nájdená.</div>
      */
     Optional<ReportedOverlimitTransaction> getTransactionById(String id);

     /***
      * <div class="en">Saves the entity using the given id.</div>
      * <div class="sk">Uloží entitu pod použitým id.</div>
      * @param id <div class="en">id which will identify the saved entity.</div>
      *           <div class="sk">id, ktoré bude reprezentovať uloženú entitu.</div>
      * @param transaction <div class="en">entity to be saved.</div>
      *                    <div class="sk">entita, ktorá sa má uložiť.</div>
      * @return <div class="en">saved entity.</div>
      * <div class="sk">uložená entita.</div>
      */
     ReportedOverlimitTransaction putTransaction(String id, ReportedOverlimitTransaction transaction);

     /***
      * <div class="en">Deletes the entity identified by the given id.</div>
      * <div class="sk">Zmaže entitu identifikovanú zadaným id.</div>
      * @param id <div class="en">id of entity.</div>
      *           <div class="sk">id entity.</div>
      * @return <div class="en">true if the entity was deleted, false it no entity found.</div>
      * <div class="sk">true ak entita bola zmazaná, false ak entita nebola nájdená.</div>
      * @throws ReportedOverlimitTransactionBadRequestException <div class="en">
      *     thrown when the entity couldn't be deleted.</div>
      *     <div class ="sk">výnimka vyvolaná, ak entita nemohla byť zmazaná.</div>
      */
     boolean deleteTransaction (String id) throws ReportedOverlimitTransactionBadRequestException;

}
