package stuba.fei.gono.java.blocking.validation.annotations;

import stuba.fei.gono.java.blocking.validation.AccountValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * Annotation for validation of AccountNO - must contain either a IBAN with optional
 * BIC, or local account number.
 */
@Constraint(validatedBy = AccountValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAccount {
    String message() default "";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
