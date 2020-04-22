package stuba.fei.gono.java.blocking.validation;

import org.springframework.beans.factory.annotation.Value;
import stuba.fei.gono.java.blocking.validation.annotations.Limit;
import stuba.fei.gono.java.pojo.Money;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LimitValidator implements ConstraintValidator<Limit, Money> {

    @Value("${reportedOverlimitTransaction.limit:999999999.99}")
    private double val;

    @Override
    public void initialize(Limit constraintAnnotation) {
        this.val = (constraintAnnotation.maxValue() == 0 ? val: constraintAnnotation.maxValue());
    }

    @Override
    public boolean isValid(Money money, ConstraintValidatorContext constraintValidatorContext) {
        if(money == null) {
            return true;
        }
        if(money.getAmount() == null) {
            return true;
        }
        return !(money.getAmount() > this.val);

    }
}
