package stuba.fei.gono.java.blocking.validation;

import org.springframework.beans.factory.annotation.Autowired;
import stuba.fei.gono.java.blocking.services.AccountService;
import stuba.fei.gono.java.blocking.validation.annotations.OnlineAccount;
import stuba.fei.gono.java.pojo.Account;
import stuba.fei.gono.java.pojo.AccountNO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/***
 * <div class="en">Class implementing validation of AccountNO object with OnlineAccount annotation.
 * AccountNo must represent a saved account
 * that is online e.g. AccountNO must have IBAN or local account number representing Account that is retrieved
 * via AccountService instance and the property isActive of this Account is true.</div>
 * <div class="sk">Trieda implementujúca validáciu objektov triedy AccountNO s OnlineAccount anotáciou.
 * Platný objekt musí reprezentovať uložený, aktívny účet, t.j. objekt musí obsahovať buď IBAN alebo
 * lokálne číslo účtu pomocou ktorého inštancia služby AccountService vie získať inštanciu triedy Account,
 * ktorá má premennú isActive nastavenú na true.</div>
 */
public class OnlineAccountValidator implements ConstraintValidator<OnlineAccount, AccountNO> {
    @Autowired
    private  AccountService accountService;

    @Override
    public void initialize(OnlineAccount constraintAnnotation) {

    }

    @Override
    public boolean isValid(AccountNO account, ConstraintValidatorContext constraintValidatorContext) {
        if(account == null)
            return false;
        Account a= null;
        if(account.getIban() != null)
            a = accountService.getAccountByIban(account.getIban()).orElse(null);
        else if (account.getLocalAccountNumber() != null)
            a = accountService.getAccountByLocalNumber(account.getLocalAccountNumber()).orElse(null);
        if(a == null)
            return false;
        if(a.getIsActive() == null)
            return false;
        return a.getIsActive();

    }
}
