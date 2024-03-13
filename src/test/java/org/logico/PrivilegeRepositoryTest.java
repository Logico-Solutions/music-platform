package org.logico;

import io.quarkus.panache.common.Sort.Direction;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.logico.model.Privilege;
import org.logico.repository.PrivilegeRepository;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class PrivilegeRepositoryTest {

    @InjectMock
    PrivilegeRepository privilegeRepository;

    @BeforeEach
    public void setup() {
        List<Privilege> mockPrivileges = List.of(
                new Privilege(1, "View Role", new HashSet<>()),
                new Privilege(2, "Create Role", new HashSet<>()),
                new Privilege(3, "Update Role", new HashSet<>())
        );

        Mockito.when(privilegeRepository.findPaginatedAndSortedById(0, 3, Direction.Ascending)).thenReturn(mockPrivileges);
        Mockito.when(privilegeRepository.findPaginatedAndSortedById(0, 2, Direction.Ascending)).thenReturn(mockPrivileges.subList(0, 2));
        Mockito.when(privilegeRepository.findPaginatedAndSortedById(10, 2, Direction.Ascending)).thenReturn(List.of());
        Mockito.when(privilegeRepository.findPaginatedAndSortedById(1, 1, Direction.Ascending)).thenReturn(List.of(mockPrivileges.get(0)));
        Mockito.when(privilegeRepository.findPaginatedAndSortedById(0, 1, Direction.Ascending)).thenReturn(List.of(mockPrivileges.get(0)));
    }

    @Test
    public void testPaginationWorksAsExpected() {
        var result = privilegeRepository.findPaginatedAndSortedById(0, 2, Direction.Ascending);

        assertThat(result, hasSize(2));
    }

    @Test
    public void testSortingByIdAscendingOrder() {
        var result = privilegeRepository.findPaginatedAndSortedById(0, 3, Direction.Ascending);

        assertThat(result, contains(
                hasProperty("name", is("View Role")),
                hasProperty("name", is("Create Role")),
                hasProperty("name", is("Update Role"))
        ));
    }

    @Test
    public void testEmptyResultOnPageBeyondDataRange() {
        var result = privilegeRepository.findPaginatedAndSortedById(5, 10, Direction.Ascending);

        assertThat(result, is(empty()));
    }

    @Test
    public void testNonEmptyResultForValidPage() {
        var result = privilegeRepository.findPaginatedAndSortedById(0, 2, Direction.Ascending);

        assertThat(result, hasSize(2));
    }

    @Test
    public void testCorrectnessOfDataInResults() {
        var result = privilegeRepository.findPaginatedAndSortedById(0, 2, Direction.Ascending);

        assertThat(result, hasSize(2));
        assertThat(result.get(0).getName(), is("View Role"));
    }
}
