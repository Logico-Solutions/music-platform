package org.logico.util;

import java.util.Arrays;

public enum RoleSortBy {
    CREATED_AT,
    ID,
    NAME,
    USERS,
    PRIVILEGE_ASSIGNMENTS;

    public static RoleSortBy fromName(String name) {
        return Arrays.stream(RoleSortBy.values())
                .filter(rsb -> rsb.name().equalsIgnoreCase(name)
                        || rsb.name().equalsIgnoreCase(name.replaceAll("([a-z])([A-Z])", "$1_$2")))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid role sorting: " + name));
    }
}
