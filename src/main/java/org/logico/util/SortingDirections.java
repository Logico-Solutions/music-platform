package org.logico.util;

import io.quarkus.panache.common.Sort.Direction;

import java.util.List;

public class SortingDirections {

    public static final Direction ASCENDING = Direction.Ascending;
    public static final Direction DESCENDING = Direction.Descending;

    public static final List<Direction> ALL_DIRECTIONS = List.of(
            ASCENDING,
            DESCENDING
    );

    public static Direction fromString(String string) {
        return ALL_DIRECTIONS
                .stream()
                .filter(d -> d.name().equalsIgnoreCase(string)
                        || d.name().equalsIgnoreCase(String.format("%sending", string)))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException(String.format("Invalid sorting direction: %s", string)));
    }
}
