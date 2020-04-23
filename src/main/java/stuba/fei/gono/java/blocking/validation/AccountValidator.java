package stuba.fei.gono.java.blocking.validation;

import stuba.fei.gono.java.pojo.Account;
import stuba.fei.gono.java.blocking.validation.annotations.ValidAccount;
import stuba.fei.gono.java.pojo.AccountNO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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
