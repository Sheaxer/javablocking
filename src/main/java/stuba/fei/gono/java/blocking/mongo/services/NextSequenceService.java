package stuba.fei.gono.java.blocking.mongo.services;

import com.mongodb.client.*;
import com.mongodb.client.model.Projections;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
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
 * Class that allows to perform operations on MongoDB.
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
     * Finds the sequence, gets id that will be used to insert new Document into MongoDB and updates the sequence
     * to generate new id.
     * @param seqName name of the sequence where to find next value of id
     * @return new id to be used to insert new Document into MongoDB
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
        /*if(counter == null)
        {
            log.info("doing new");
            mongoOperations.executeCommand("{db.sequence.insert({_id: \""+seqName+"\",seq: 2})}");
            return "1";
        }*/

        return String.valueOf( counter.getSeq());
    }

    /***
     * Sets value of sequence in MongoDB.
     * @param seqName name of the sequence
     * @param value value that the sequence will be set to
     */
    private void setNextSequence(@NotNull String seqName,@NotNull String value)
    {
        SequenceId s = mongoOperations.findAndModify(
                query(where("_id").is(seqName)),
                new Update().set("seq",Long.valueOf(value)),
                options().returnNew(true).upsert(true),
                SequenceId.class
        );
        /*if(s==null)
        {
            mongoOperations.executeCommand("{db.sequence.insert({_id: \""+seqName+"\",seq: "+value+"})}");
        }*/
    }

    /***
     * Generates the next id to be used when saving entity using given repository and updates the sequence
     * with the given name.
     * @param rep repository where the entity will be saved.
     * @param sequenceName name of the sequence that holds the maximal value of id of entities saved in repository.
     * @return new id value.
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
            //newId = this.getNextSequence(sequenceName);
            this.setNextSequence(sequenceName, newId);
            log.info("wasModified");
        }
        return newId;
    }

    /***
     * Finds the maximal value of id of saved entities sof given class.
     * @param rep class of entities.
     * @return maxila value of id of saved entities of given class.
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
     * Checks if the sequence with given name needs to update its maximal id value by the given value.
     * @param seqName - name of the sequence, must not be null.
     * @param value - value to be checked against maximal id value, must not be null.
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
