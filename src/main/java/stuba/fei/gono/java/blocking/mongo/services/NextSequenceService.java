package stuba.fei.gono.java.blocking.mongo.services;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Projections;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import stuba.fei.gono.java.blocking.mongo.repositories.*;
import stuba.fei.gono.java.blocking.pojo.ReportedOverlimitTransaction;
import stuba.fei.gono.java.pojo.*;

import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/***
 * <div class="en">Service that generates next id value for storing data in MongoDB.</div>
 * <div class="sk">Služba, ktorá generuje nasledujúcu hodnotu id použitú na uloženie entity do Mongo databázy.</div>
 */
@Service
@Slf4j
public class NextSequenceService {
    private final MongoOperations mongoOperations;

    @Autowired
    public NextSequenceService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    /***
     * <div class="en">Increments the value of sequence with the given
     * sequence name.</div>
     * <div class="sk">Inkrementuje hodnotu sekvencie so zadaným menom.</div>
     * @see SequenceId
     * @param seqName <div class="en">name of the sequence.</div>
     *                <div class="sk">názov sekvencie.</div>
     * @return <div class="en">updated value of the sequence.</div>
     * <div class="sk">aktualizovná hodnota sekvencie.</div>
     */
    private String getNextSequence(@NotNull String seqName)
    {
        SequenceId counter = mongoOperations.findAndModify(
                query(where("_id").is(seqName)),
                new Update().inc("seq",1),
                options().returnNew(true).upsert(true),
                SequenceId.class);
        if(counter == null)
        {
            setNextSequence(seqName,String.valueOf(1));
            return "1";
        }

        return String.valueOf( counter.getSeq());
    }

    /***
     * <div class="en">Sets value of sequence with the given name.</div>
     * <div class="sk">Nastaví hodnotu sekvencie so zadaným názvom.</div>
     * @see SequenceId
     * @param seqName <div class="en">name of the sequence.</div>
     *                <div class="sk">názov sekvencie.</div>
     * @param value <div class="en">value that the sequence will be set to.</div>
     *              <div class="sk">hodnota na ktorú sa sekvencia nastaví.</div>
     */
    private void setNextSequence(@NotNull String seqName,@NotNull String value)
    {
         mongoOperations.findAndModify(
                query(where("_id").is(seqName)),
                new Update().set("seq",Long.valueOf(value)),
                options().returnNew(true).upsert(true),
                SequenceId.class
        );
    }

    /***
     * <div class="en">Generates a new value of an id for saving new object in a database.
     * Updates maximal value
     * of sequence with the given name, checks if an entity with this id already exists in the repository.
     * If it does exist, function finds the actual maximal value of id used to store entities in the repository and
     * updates the sequence.</div>
     * <div class="sk">Generuje novú hodnotu id na uloženie nového objektu do databázy. Aktualizuje
     * maximálnu hodnotu sekvencie so zadaným menom, skontroluje či už existuje entita so zadaným id. Ak existuje,
     * využije ďalšiu metódu na získanie skutočnej maximálnej hodnoty id v zadanom repozitáry a aktualizuje hodnotu
     * sekvencie.</div>
     * @param rep <div class="en">repository where the object will be saved.</div>
     *            <div class="sk">repozitár v ktorom bude objekt uložený.</div>
     * @param sequenceName <div class="en">name of the sequence holding the id of last saved object.</div>
     *                     <div class="sk">názov sekvencie ktorá udržiava id posledného uloženého objektu.</div>
     * @return <div class="en">value of id that should be used to save object in
     * the given repository.</div>
     * <div class="sk">hodntota id ktoré by malo byť použité na uloženie
     * objektu v zadanom repozitári.</div>
     */
    public String getNewId(@NotNull CrudRepository<?,String> rep, @NotNull String sequenceName)
    {
        String newId = this.getNextSequence(sequenceName);
        if( rep.findById(newId).isPresent())
        {
            if(rep instanceof ReportedOverlimitTransactionRepository)
            {
                newId= lastId(ReportedOverlimitTransaction.class);
            }
            else if(rep instanceof ClientRepository)
                newId = lastId(Client.class);
            else if(rep instanceof EmployeeRepository)
                newId = lastId(Employee.class);
            else if(rep instanceof OrganisationUnitRepository)
                newId = lastId(OrganisationUnit.class);
            else if(rep instanceof AccountRepository)
                newId = lastId(Account.class);
            this.setNextSequence(sequenceName, newId);
        }
        return newId;
    }

    /***
     * <div class="en">Calculates the maximal id that was used to save an object of the given class.
     * Transforms ids of all entities of the given class into long and finds the maximal value.</div>
     * <div class="sk">Získa maximálnu hodnotu id ktoré bolo použité na uloženie objektu zadanej triedy.
     * Transformuje id všetkých entít triedy do typu long a získa maximálnu hodnotu.</div>
     * @param rep <div class="en">class of the entities, must not be null.</div>
     *            <div class="sk">trieda entít, nesmie byť null.</div>
     * @return <div class="en">string value of the maximal id of saved entity of the given class.</div>
     * <div class="sk">hodnota maximálneho id použitého na uloženie entity zadanej triedy.</div>
     */
    private String lastId(@NotNull Class<?> rep)
    {
        return mongoOperations.execute(rep, mongoCollection -> {
           FindIterable<Document> doc= mongoCollection.find().projection(Projections.include("_id"));
           MongoIterable<Long> s = doc.map(document ->
           {
               try{
                   return Long.parseLong(document.getString("_id"));
               }
               catch(NumberFormatException e)
               {
                   return 0L;
               }
           });
           Long lastVal=0L;
            for (Long tmp : s) {
                lastVal = tmp > lastVal ? tmp : lastVal;
            }
            lastVal++;
           return String.valueOf(lastVal);
        });
    }

    /***
     * <div class="en">Checks if the sequence with given name needs to update
     * its maximal value. If the given value is larger than the maximal value stored in
     * the sequence with the given name, it sets it to the new value.</div>
     * <div class="sk">Skontroluje, či sekvencia so zadaným názvom potrebuje aktualizovať
     * maximálnu hodnotu id. Ak je zadaná hodnota väčšia ako uložená maximálna hodnota, nastaví
     * sa táto uložená hodnota na novú.</div>
     * @param seqName <div class="en">name of the sequence, must not be null.</div>
     *                <div class="sk">názov sekvencie, nesmie byť null.</div>
     * @param value <div class="en">value to be checked against maximal id value, must not be null.</div>
     *            <div class="sk">hodnota oproti ktorej sa uložená maximálna hodnota porovná.</div>
     */
    public void needsUpdate(String seqName, String value)
    {
        try {

            long longVal = Long.parseLong(value);
            List<SequenceId> tmp = mongoOperations.find(query(where("_id").is(seqName)), SequenceId.class);
            if (tmp.isEmpty())
                tmp.add(new SequenceId());
            if(longVal > tmp.get(0).getSeq())
                setNextSequence(seqName,value);
        }catch (NumberFormatException ignored)
        {
        }

    }
}
