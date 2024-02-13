package org.logico.util;

public enum RoleSortBy {
    CREATED_AT,
    ID,
    NAME,
    USERS,
    PRIVILEGE_ASSIGNMENTS;

    public static RoleSortBy fromString(String string) {
        try {
            return valueOf(string.toUpperCase());
        } catch (IllegalArgumentException e) {
            String upperCaseString = string.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
            try {
                return valueOf(upperCaseString);
            } catch (IllegalArgumentException e2) {
                throw new IllegalArgumentException("Unsupported sorting criteria: " + string);
            }
        }
    }
}
