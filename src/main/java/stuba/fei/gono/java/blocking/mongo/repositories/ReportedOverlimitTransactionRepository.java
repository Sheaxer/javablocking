package stuba.fei.gono.java.blocking.mongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import stuba.fei.gono.java.pojo.ReportedOverlimitTransaction;

public interface ReportedOverlimitTransactionRepository extends MongoRepository<ReportedOverlimitTransaction, String>
{

    /*@Override
    Optional<ReportedOverlimitTransactionRepository> findById(String s);

    @Override
    void delete(ReportedOverlimitTransactionRepository reportedOverlimitTransactionRepository);

    @Override
    <S extends ReportedOverlimitTransactionRepository> S save(S s);*/
}
