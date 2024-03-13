package org.logico.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.logico.validation.validator.RoleSortByValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleSortByValidator.class)
public @interface ValidRoleSortBy {

    String message() default "Invalid Role sort by attribute";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
