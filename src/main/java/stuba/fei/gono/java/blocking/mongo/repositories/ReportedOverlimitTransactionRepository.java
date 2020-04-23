package stuba.fei.gono.java.blocking.mongo.repositories;

import org.springframework.data.repository.CrudRepository;
import stuba.fei.gono.java.blocking.pojo.ReportedOverlimitTransaction;

/***
 * Interface extending CrudRepository for ReportedOverlimitTransaction.
 * @see ReportedOverlimitTransaction
 * @see CrudRepository
 */
public interface ReportedOverlimitTransactionRepository extends CrudRepository<ReportedOverlimitTransaction, String>
{ }
