package org.logico.util;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class RoleSortByTest {

    @Test
    public void shouldConvertToEnum() {
        String test1 = "privilegeAssignments";
        String test2 = "privilege_assignments";
        RoleSortBy expected = RoleSortBy.PRIVILEGE_ASSIGNMENTS;
        assertEquals(expected, RoleSortBy.fromString(test1));
        assertEquals(expected, RoleSortBy.fromString(test2));
    }

    @Test
    public void shouldThrowIllegalArgument() {
        String test = "test";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> RoleSortBy.fromString(test));

        String expected = "Unsupported sorting: " + test;
        String actual = exception.getMessage();

        assertTrue(actual.contains(expected));
    }
}
