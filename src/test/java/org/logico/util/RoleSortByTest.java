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
        assertEquals(expected, RoleSortBy.fromName(test1));
        assertEquals(expected, RoleSortBy.fromName(test2));
    }

    @Test
    public void shouldThrowIllegalArgument() {
        String test = "test";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> RoleSortBy.fromName(test));

        String expected = "Invalid role sorting: " + test;
        String actual = exception.getMessage();

        assertTrue(actual.contains(expected));
    }
}
