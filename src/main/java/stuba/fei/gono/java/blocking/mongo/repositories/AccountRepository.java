package stuba.fei.gono.java.blocking.mongo.repositories;

import org.springframework.data.repository.CrudRepository;
import stuba.fei.gono.java.pojo.Account;

import java.util.Optional;

/***
 * <div class="en">Interface extending CrudRepository for Account.</div>
 * <div class="sk">Rozhranie rozširujúce CrudRepository pre objekty triedy Account.</div>
 * @see Account
 * @see CrudRepository
 */
public interface AccountRepository extends CrudRepository<Account,String> {
    /***
     * <div class="en">Finds the entity identified by the given IBAN.</div>
     * <div class="sk">Nájde entitu identifikovanú zadaným IBAN-om</div>
     * @param iban <div class="en">IBAN of entity.</div>
     *             <div class="sk">IBAN entity</div>
     * @return <div class="en">Optional containing the entity or Optional.empty() if no entity is found.</div>
     * <div class="sk">Optional obsahujúci entitu alebo Optional.empty(), ak entita nebola nájdená.</div>
     */
    Optional<Account> getAccountByIban(String iban);

    /***
     * <div class="en">Finds the entity identified by the given Local Account Number.</div>
     * <div class="sk">Nájde entitu identifikovanú lokálnym číslom účtu.</div>
     * @param localAccountNumber <div class="en">Local Account Number of entity.</div>
     *                           <div class="sk">lokálne číslo účtu.</div>
     * @return <div class="en">Optional containing the entity or Optional.empty() if no entity is found.</div>
     * <div class="sk">Optional obsahujúci entitu alebo Optional.empty(), ak entita nebola nájdená.</div>
     */
    Optional<Account> getAccountByLocalAccountNumber(String localAccountNumber);
}
