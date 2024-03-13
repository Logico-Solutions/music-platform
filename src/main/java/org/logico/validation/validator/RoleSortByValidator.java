package org.logico.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.logico.model.AuditEntity;
import org.logico.model.Role;
import org.logico.validation.annotation.ValidRoleSortBy;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

public class RoleSortByValidator implements ConstraintValidator<ValidRoleSortBy, String> {

    private static final List<Field> ROLE_FIELDS;

    static {
        ROLE_FIELDS = Stream
                .of(Role.class.getDeclaredFields(), AuditEntity.class.getDeclaredFields())
                .flatMap(Stream::of)
                .toList();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return ROLE_FIELDS.stream()
                .map(Field::getName)
                .anyMatch(f -> f.equals(value));
    }

    @Override
    public void initialize(ValidRoleSortBy constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
