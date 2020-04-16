package stuba.fei.gono.java.blocking.mongo.services;

import com.mongodb.client.*;
import com.mongodb.client.model.Projections;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import stuba.fei.gono.java.blocking.mongo.repositories.ClientRepository;
import stuba.fei.gono.java.blocking.mongo.repositories.EmployeeRepository;
import stuba.fei.gono.java.blocking.mongo.repositories.OrganisationUnitRepository;
import stuba.fei.gono.java.blocking.mongo.repositories.ReportedOverlimitTransactionRepository;
import stuba.fei.gono.java.blocking.pojo.ReportedOverlimitTransaction;
import stuba.fei.gono.java.pojo.*;

import javax.validation.constraints.NotNull;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/***
 * Class that allows to perform operations on MongoDB
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
    public String getNextSequence(@NotNull String seqName)
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
    public void setNextSequence(@NotNull String seqName,@NotNull String value)
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

    public String getNewId(@NotNull MongoRepository<?,String> rep, @NotNull String sequenceName)
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
            //newId = this.getNextSequence(sequenceName);
            this.setNextSequence(sequenceName, newId);
            log.info("wasModified");
        }
        return newId;
    }

    public String lastId(@NotNull Class<?> rep)
    {
        return mongoOperations.execute(rep, mongoCollection -> {
           FindIterable<Document> doc= mongoCollection.find().projection(Projections.include("_id"));
           Long max=0L;

           MongoIterable<Long> s = doc.map(document -> Long.valueOf(document.getString("_id")));
           Long lastVal=0L;
            for (Long tmp : s) {
                lastVal = tmp > lastVal ? tmp : lastVal;
            }
            lastVal++;
           return String.valueOf(lastVal);
        });
    }
}
