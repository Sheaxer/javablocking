package stuba.fei.gono.java.blocking.services;

import stuba.fei.gono.java.pojo.Account;

import java.util.Optional;

/***
 * <div class="en">Interface for marshalling and de-marshalling Account entities.</div>
 * <div class="sk">Rozhranie na marshalling a de-marshalling entít triedy Account.</div>
 */
public interface AccountService {
    /***
     * <div class="en">
     * Finds the entity with identified by the given IBAN.</div>
     * <div class="sk">Nájde entitu identifikovanú zadaným IBAN-om.</div>
     * @param iban <div class="en">IBAN of the entity.</div>
     *             <div class="sk">IBAN identifikujúci entitu.</div>
     * @return <div class="en">Optional containing the entity or Optional.empty() if no entity was found.</div>
     * <div class="sk">Optional obsahujúci entitu alebo Optional.empty() ak entita nebola nájdená.</div>
     */
    Optional<Account> getAccountByIban(String iban);

    /***
     * <div class="en">Finds the entity with identified by the given Local Account Number.</div>
     * <div class="sk">Nájde entitu identifikovanú zadaným lokálnym číslom účtu.</div>
     * @param localNumber <div class="en">Local Account Number of the entity.</div>
     *                    <div class="sk">lokálne číslo účtu.</div>
     * @return <div class="en">Optional containing the entity or Optional.empty() if no entity was found.</div>
     * <div class="sk">Optional obsahujúci entitu alebo Optional.empty() ak entita nebola nájdená.</div>
     */
    Optional<Account> getAccountByLocalNumber(String localNumber);
}
