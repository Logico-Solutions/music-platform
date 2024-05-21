package org.logico.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PrivilegeAssignmentType {
    ALLOWED("Allowed", "Direct access to the functionality"),
    REQUEST("Request", "Requires approval for the functionality");

    private final String name;
    private final String description;

    @Override
    @JsonValue
    public String toString() {
        return this.name;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static PrivilegeAssignmentType fromName(String name) {
        return Arrays.stream(PrivilegeAssignmentType.values())
                .filter(type -> type.getName().equals(name))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException(String.format("Invalid privilege type '%s'", name)));
    }
}