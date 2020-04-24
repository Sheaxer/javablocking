package stuba.fei.gono.java.blocking.validation.annotations;

import stuba.fei.gono.java.blocking.validation.BankingDayValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * Annotation for validation of Banking day - currently any non weekend day.
 */
@Target(ElementType.FIELD)
@Constraint(validatedBy = BankingDayValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface BankingDay {
    String message() default "";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
