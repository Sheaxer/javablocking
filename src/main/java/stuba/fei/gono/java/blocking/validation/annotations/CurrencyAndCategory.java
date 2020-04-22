package stuba.fei.gono.java.blocking.validation.annotations;

import stuba.fei.gono.java.blocking.validation.CurrencyAndCategoryValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CurrencyAndCategoryValidator.class)
public @interface CurrencyAndCategory {String message() default "";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
