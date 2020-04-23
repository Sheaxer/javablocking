package stuba.fei.gono.java.blocking.mongo.repositories;

import org.springframework.data.repository.CrudRepository;
import stuba.fei.gono.java.pojo.Account;

import java.util.Optional;

/***
 * Interface extending CrudRepository for Account.
 * @see Account
 * @see CrudRepository
 */
public interface AccountRepository extends CrudRepository<Account,String> {
    /***
     * Retrieves entity identified by the given IBAN.
     * @param iban IBAN of entity.
     * @return Optional containing the entity or Optional.empty() if no entity is found.
     */
    Optional<Account> getAccountByIban(String iban);

    /***
     * Retrieves entity identified by the given Local Account Number.
     * @param localAccountNumber Local Account Number of entity.
     * @return Optional containing the entity or Optional.empty() if no entity is found.
     */
    Optional<Account> getAccountByLocalAccountNumber(String localAccountNumber);
}
