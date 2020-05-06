package stuba.fei.gono.java.blocking.validation;

import stuba.fei.gono.java.blocking.validation.annotations.ValidAccount;
import stuba.fei.gono.java.pojo.AccountNO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/***
 * <div class="en">Class implementing validation of ValidAccount annotation.
 * Valid AccountNO must contain either IBAN with optional BIC or local account number.</div>
 * <div class="sk">Trieda implementujúca validáciu objektu triedy AccountNO s ValidAccount anotáciou.
 * Platný objekt AccoutNO musí obsahovať buď - IBAN a volitelný BIC - alebo lokálne číslo účtu.</div>
 * @see ValidAccount
 */
public class AccountValidator implements ConstraintValidator<ValidAccount, AccountNO> {

    @Override
    public void initialize(ValidAccount constraintAnnotation) {

    }

    @Override
    public boolean isValid(AccountNO account, ConstraintValidatorContext constraintValidatorContext) {
        if(account == null)
            return true;
        if((account.getIban() == null) || (account.getIban().isEmpty()))
        {
            return (account.getBic() != null) && (!account.getBic().isEmpty())
                    && (account.getLocalAccountNumber() != null) && (!account.getLocalAccountNumber().isEmpty());
        }
        return true;

    }
}
