package stuba.fei.gono.java.blocking.validation.annotations;

import stuba.fei.gono.java.blocking.validation.DaysBeforeDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * Annotation for validation of date - must be at least x number of days
 * (property days) in the future from the time of validation.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DaysBeforeDateValidator.class)
public @interface DaysBeforeDate {
    String message() default "";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    long days() default 0L;
}
