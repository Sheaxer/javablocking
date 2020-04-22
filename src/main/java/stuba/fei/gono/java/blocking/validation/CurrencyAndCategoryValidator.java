package stuba.fei.gono.java.blocking.validation;

import stuba.fei.gono.java.blocking.pojo.ReportedOverlimitTransaction;
import stuba.fei.gono.java.blocking.validation.annotations.CurrencyAndCategory;
import stuba.fei.gono.java.pojo.Currency;
import stuba.fei.gono.java.pojo.OrderCategory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrencyAndCategoryValidator implements ConstraintValidator<CurrencyAndCategory, ReportedOverlimitTransaction> {

    @Override
    public void initialize(CurrencyAndCategory constraintAnnotation) {

    }

    @Override
    public boolean isValid(ReportedOverlimitTransaction reportedOverlimitTransaction, ConstraintValidatorContext constraintValidatorContext) {
        if(reportedOverlimitTransaction == null)
            return true;
        if(reportedOverlimitTransaction.getOrderCategory() == null)
            return true;
        if(reportedOverlimitTransaction.getAmount() == null)
            return true;
        if(reportedOverlimitTransaction.getOrderCategory().equals(OrderCategory.FX) && reportedOverlimitTransaction.getAmount().getCurrency().equals(Currency.EUR))
            return false;
        return !reportedOverlimitTransaction.getOrderCategory().equals(OrderCategory.DOMESTIC) || reportedOverlimitTransaction.getAmount().getCurrency().equals(Currency.EUR);
    }
}
