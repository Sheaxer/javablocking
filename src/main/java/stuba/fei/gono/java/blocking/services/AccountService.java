package stuba.fei.gono.java.blocking.services;

import stuba.fei.gono.java.pojo.Account;

import java.util.Optional;

/***
 * Interface for marshalling and de-marshalling Account entities.
 */
public interface AccountService {
    /***
     * Finds the entity with identified by the given IBAN.
     * @param iban IBAN of the entity.
     * @return Optional containing the entity or Optional.empty() if no entity was found.
     */
    Optional<Account> getAccountByIban(String iban);
    /***
     * Finds the entity with identified by the given Local Account Number.
     * @param localNumber Local Account Number of the entity.
     * @return Optional containing the entity or Optional.empty() if no entity was found.
     */
    Optional<Account> getAccountByLocalNumber(String localNumber);
}
