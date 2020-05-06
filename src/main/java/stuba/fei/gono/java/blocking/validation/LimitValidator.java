package stuba.fei.gono.java.blocking.validation;

import org.springframework.beans.factory.annotation.Value;
import stuba.fei.gono.java.blocking.validation.annotations.Limit;
import stuba.fei.gono.java.pojo.Money;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/***
 * <div class="en">Class implementing validation of Money object with Limit annotation.
 * Valid amount on withdrawal must be less than limit.</div>
 * <div class="sk">Trieda implementujúca valídáciu objektov triedy Money s anotáciou Limit.
 * Platný objekt musí mať hodnotu premennej amount menšiu ako limit.</div>
 * @see Limit
 */
public class LimitValidator implements ConstraintValidator<Limit, Money> {
    /***
     * <div class="en">Maximal value of the valid amount in Money object.
     * Set with either limit property of Limit annotation
     * or reportedOverlimitTransaction.limit property, default 999999999.99</div>
     * <div class="sk">Maximálna hodntota amount premennej platného objektu.
     * Nastavená buď premennou limit v Limit anotácii, alebo property reportedOverlimitTransaction.limit,
     * alebo predvolenou hodnota 999999999.99</div>
     */
    @Value("${reportedOverlimitTransaction.limit:999999999.99}")
    private double val;

    @Override
    public void initialize(Limit constraintAnnotation) {
        this.val = (constraintAnnotation.limit() == 0 ? val: constraintAnnotation.limit());
    }

    @Override
    public boolean isValid(Money money, ConstraintValidatorContext constraintValidatorContext) {
        if(money == null) {
            return true;
        }
        return !(money.getAmount() > this.val);

    }
}
