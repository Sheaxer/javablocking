package stuba.fei.gono.java.blocking.mongo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import stuba.fei.gono.java.blocking.mongo.repositories.AccountRepository;
import stuba.fei.gono.java.blocking.mongo.services.NextSequenceService;
import stuba.fei.gono.java.errors.ReportedOverlimitTransactionException;
import stuba.fei.gono.java.blocking.mongo.repositories.ReportedOverlimitTransactionRepository;
import stuba.fei.gono.java.blocking.pojo.ReportedOverlimitTransaction;
import stuba.fei.gono.java.pojo.Account;
import stuba.fei.gono.java.pojo.State;
import stuba.fei.gono.java.blocking.services.ReportedOverlimitTransactionService;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Optional;
@Service
public class ReportedOverlimitTransactionServiceMongoImpl implements ReportedOverlimitTransactionService {

    @Value("${reportedOverlimitTransaction.transaction.sequenceName:customSequences}")
    private String sequenceName;
    //private ClientRepository clientRepository;
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
        //transactionRepository.save(newTransaction);
        return putTransaction(newId,newTransaction);
    }

    @Override
    public Optional<ReportedOverlimitTransaction> getTransactionById(@NotNull String id) throws ReportedOverlimitTransactionException {
        return transactionRepository.findById(id);
        /*if(transaction.isPresent())
            return transaction.get();
        else
            throw new ReportedOverlimitTransactionException("ID_NOT_FOUND");*/
       // return transaction.orElseThrow(() -> new ReportedOverlimitTransactionException("ID_NOT_FOUND"));
    }

    @Override
    public ReportedOverlimitTransaction putTransaction(@NotNull String id, @NotNull ReportedOverlimitTransaction transaction) {
        transaction.setId(id);
        transaction.setModificationDate(OffsetDateTime.now());
        transaction.setZoneOffset(transaction.getModificationDate().getOffset().getId());
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public boolean deleteTransaction(@NotNull String id) {
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
                throw new ReportedOverlimitTransactionException("STATE_CLOSED");

        }
        else
        {
           return false;
        }
    }
}
