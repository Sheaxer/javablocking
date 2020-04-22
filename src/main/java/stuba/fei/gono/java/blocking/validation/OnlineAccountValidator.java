package stuba.fei.gono.java.blocking.validation;

import org.springframework.beans.factory.annotation.Autowired;
import stuba.fei.gono.java.blocking.services.AccountService;
import stuba.fei.gono.java.blocking.validation.annotations.OnlineAccount;
import stuba.fei.gono.java.pojo.Account;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OnlineAccountValidator implements ConstraintValidator<OnlineAccount, Account> {
    @Autowired
    private  AccountService accountService;

    @Override
    public void initialize(OnlineAccount constraintAnnotation) {

    }

    @Override
    public boolean isValid(Account account, ConstraintValidatorContext constraintValidatorContext) {
        if(account == null)
            return false;
        Account a= null;
        if(account.getIban() != null)
            a = accountService.getAccountByIban(account.getIban());
        else if (account.getLocalAccountNumber() != null)
            a = accountService.getAccountByLocalNumber(account.getLocalAccountNumber());
        if(a == null)
            return false;
        return a.isActive();

    }
}
