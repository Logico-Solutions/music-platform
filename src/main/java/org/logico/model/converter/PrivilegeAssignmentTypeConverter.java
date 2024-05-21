package org.logico.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.logico.model.PrivilegeAssignmentType;

import java.util.Arrays;

@Converter(autoApply = true)
public class PrivilegeAssignmentTypeConverter
        implements AttributeConverter<PrivilegeAssignmentType, String> {

    @Override
    public String convertToDatabaseColumn(PrivilegeAssignmentType type) {
        return type.getName();
    }

    @Override
    public PrivilegeAssignmentType convertToEntityAttribute(String dbData) {
        return Arrays.stream(PrivilegeAssignmentType.values())
                .filter(type -> type.getName().equals(dbData))
                .findFirst()
                .orElse(null);
    }
}