package stuba.fei.gono.java.blocking.services;


import org.springframework.stereotype.Service;
import stuba.fei.gono.java.blocking.pojo.ReportedOverlimitTransaction;
import stuba.fei.gono.java.errors.ReportedOverlimitTransactionException;

import java.util.Optional;


public interface ReportedOverlimitTransactionService {

     ReportedOverlimitTransaction postTransaction(ReportedOverlimitTransaction transaction);
     Optional<ReportedOverlimitTransaction> getTransactionById(String id);
     ReportedOverlimitTransaction putTransaction(String id, ReportedOverlimitTransaction transaction);
     boolean deleteTransaction (String id) throws ReportedOverlimitTransactionException;

}
