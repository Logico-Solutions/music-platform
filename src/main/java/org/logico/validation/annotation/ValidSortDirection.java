package org.logico.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.logico.validation.validator.SortDirectionValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SortDirectionValidator.class)
public @interface ValidSortDirection {

    String message() default "Invalid sorting direction";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
