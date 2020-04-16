package stuba.fei.gono.java.blocking.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import stuba.fei.gono.java.pojo.Money;
import stuba.fei.gono.java.blocking.validation.annotations.MaxAmount;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
@Slf4j
public class MaxAmountValidator implements ConstraintValidator<MaxAmount, Money> {



    @Value("${reportedOverlimitTransaction.maxAmmount:999999999.99}")
    private double customValue;

    private double val;

    @Override
    public void initialize(MaxAmount constraintAnnotation) {
       // this.val = constraintAnnotation.maxValue();
        this.val = (constraintAnnotation.maxValue() == 0 ? customValue: constraintAnnotation.maxValue());
    }

    @Override
    public boolean isValid(Money money, ConstraintValidatorContext constraintValidatorContext) {
        if(money == null)
            return true;
        log.info("Checking");
        log.info(String.valueOf(val));

        if(money.getAmount()<=0.0)
            return false;

        return !(money.getAmount() > val);
    }


}
