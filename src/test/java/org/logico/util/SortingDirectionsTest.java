package org.logico.util;

import io.quarkus.panache.common.Sort.Direction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class SortingDirectionsTest {

    @Test
    public void shouldConvertToDirection() {
        String test1 = "ascending";
        String test2 = "ASC";
        Direction expected = SortingDirections.ASCENDING;
        assertEquals(expected, SortingDirections.fromString(test1));
        assertEquals(expected, SortingDirections.fromString(test2));
    }

    @Test
    public void shouldThrowIllegalArgument() {
        String test = "test";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> SortingDirections.fromString(test));

        String expected = String.format("Invalid sorting direction: %s", test);
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }
}
