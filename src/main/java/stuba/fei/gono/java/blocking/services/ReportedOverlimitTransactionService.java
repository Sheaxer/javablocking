package stuba.fei.gono.java.blocking.services;


import org.springframework.stereotype.Service;
import stuba.fei.gono.java.blocking.pojo.ReportedOverlimitTransaction;

@Service
public interface ReportedOverlimitTransactionService {

     ReportedOverlimitTransaction postTransaction(ReportedOverlimitTransaction transaction);
     ReportedOverlimitTransaction getTransactionById(String id);
     ReportedOverlimitTransaction putTransaction(String id, ReportedOverlimitTransaction transaction);
     ReportedOverlimitTransaction deleteTransaction (String id);

}
