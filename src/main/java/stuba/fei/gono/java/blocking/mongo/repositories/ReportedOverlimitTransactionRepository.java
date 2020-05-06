package stuba.fei.gono.java.blocking.mongo.repositories;

import org.springframework.data.repository.CrudRepository;
import stuba.fei.gono.java.blocking.pojo.ReportedOverlimitTransaction;

/***
 * <div class="en">Interface extending CrudRepository for ReportedOverlimitTransaction.</div>
 * <div class="sk">Rozhranie rozširujúce CrudRepository pre objekty triedy ReportedOverlimitTransaction.</div>
 * @see ReportedOverlimitTransaction
 * @see CrudRepository
 */
public interface ReportedOverlimitTransactionRepository extends CrudRepository<ReportedOverlimitTransaction, String>
{ }
