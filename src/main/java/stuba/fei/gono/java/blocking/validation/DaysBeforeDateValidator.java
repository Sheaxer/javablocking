package stuba.fei.gono.java.blocking.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import stuba.fei.gono.java.blocking.validation.annotations.DaysBeforeDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/***
 * Class implementing validation for DaysBeforeDate annotation. Date must be at least x number of in the future
 * from the moment of validation.
 * @see DaysBeforeDate
 */
@Slf4j
public class DaysBeforeDateValidator implements ConstraintValidator<DaysBeforeDate, Date> {

    private Date today;

    /***
     * Minimal number of days - days property of DaysBeforeDate annotation or if not used
     * reportedOverlimitTransaction.daysBefore property, default 3.
     */
    @Value("${reportedOverlimitTransaction.daysBefore:3}")
    private long days;

    @Override
    public void initialize(DaysBeforeDate constraintAnnotation) {
        this.today = new Date();
        days = (constraintAnnotation.days() == 0 ? days : constraintAnnotation.days());
        log.info(String.valueOf(days));
        /*if(days==0)
            days = cDays;*/
        log.info(String.valueOf(days));
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
       if(date ==null)
           return true;
        long diff = date.getTime() - today.getTime();
        //System.out.println(diff);
        log.info(String.valueOf(diff));
        if(diff <0)
            return true;
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) >= days;
    }
}
