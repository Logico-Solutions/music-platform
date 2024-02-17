package org.logico.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.logico.util.SortingDirections;
import org.logico.validation.annotation.ValidSortDirection;

public class SortDirectionValidator implements ConstraintValidator<ValidSortDirection, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return SortingDirections.ALL_DIRECTIONS
                .stream()
                .anyMatch(d -> d.name().equalsIgnoreCase(value) ||
                        d.name().equalsIgnoreCase(String.format("%sending", value)));
    }

    @Override
    public void initialize(ValidSortDirection constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
