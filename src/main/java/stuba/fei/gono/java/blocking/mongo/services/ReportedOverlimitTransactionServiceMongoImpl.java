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
public class ReportedOverlimitTransactionServiceMongoImpl implements ReportedOverlimitTransactionService {

    @Value("${reportedOverlimitTransaction.transaction.sequenceName:customSequences}")
    private String sequenceName;
    private ReportedOverlimitTransactionRepository transactionRepository;
    private NextSequenceService nextSequenceService;

    @Autowired
    public ReportedOverlimitTransactionServiceMongoImpl(ReportedOverlimitTransactionRepository transactionRepository,
                                                        NextSequenceService nextSequenceService)
    {
        this.transactionRepository = transactionRepository;
        this.nextSequenceService = nextSequenceService;
    }

    @Override
    public ReportedOverlimitTransaction postTransaction(@NotNull ReportedOverlimitTransaction newTransaction) {

        String newId = nextSequenceService.getNewId(transactionRepository,sequenceName);
        newTransaction.setState(State.CREATED);
        newTransaction.setModificationDate(OffsetDateTime.now());
        return putTransaction(newId,newTransaction);
    }

    @Override
    public Optional<ReportedOverlimitTransaction> getTransactionById(@NotNull String id) throws ReportedOverlimitTransactionNotFoundException {
        return transactionRepository.findById(id);
    }

    @Override
    public ReportedOverlimitTransaction putTransaction(@NotNull String id,
                                                       @NotNull ReportedOverlimitTransaction transaction) {
        transaction.setId(id);
        transaction.setModificationDate(OffsetDateTime.now());
        nextSequenceService.needsUpdate(sequenceName,id);
        transactionRepository.save(transaction);
        return transaction;
    }

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
