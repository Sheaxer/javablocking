package stuba.fei.gono.java.blocking.validation;

import stuba.fei.gono.java.blocking.validation.annotations.BankingDay;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;

/***
 * <div class="en">Class implementing the validation of BankingDay annotation of Date class. Date must not be
 * on a weekend.</div>
 * <div class="sk">Trieda implementujúca validáciu objetku triedy Date anotovanú BankingDay. Platný
 * dátum nesmie byť víkend.</div>
 * @see BankingDay
 */
public class BankingDayValidator implements ConstraintValidator<BankingDay, Date> {
    @Override
    public void initialize(BankingDay constraintAnnotation) {

    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if(date == null)
            return true;
       Calendar c = Calendar.getInstance();
       c.setTime(date);
        int day = c.get(Calendar.DAY_OF_WEEK);
        return (day != Calendar.SATURDAY) && (day != Calendar.SUNDAY);
    }
}
