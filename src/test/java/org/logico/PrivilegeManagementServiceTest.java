package org.logico;

import io.quarkus.panache.common.Sort.Direction;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.logico.dto.PrivilegeDto;
import org.logico.dto.response.PrivilegePageAndSortResponseDto;
import org.logico.service.PrivilegeManagementService;

import java.util.Collections;
import java.util.List;

import static io.smallrye.common.constraint.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@QuarkusTest
public class PrivilegeManagementServiceTest {

    @InjectMock
    PrivilegeManagementService privilegeManagementService;

    @Test
    public void shouldPaginateAndSortPrivilegesCorrectly() {
        var privilegesPage1 = List.of(new PrivilegeDto(1, "View Role"),
                new PrivilegeDto(2, "Create Role"));
        var privilegePageAndSortResponseDto = new PrivilegePageAndSortResponseDto(privilegesPage1, 0, 2, 4,2, true, false);

        when(privilegeManagementService.findPrivilegesWithPaginationAndSorting(0,2, Direction.Ascending))
               .thenReturn(privilegePageAndSortResponseDto);

         assertEquals(privilegePageAndSortResponseDto, privilegeManagementService.findPrivilegesWithPaginationAndSorting(0,2, Direction.Ascending));
    }
    @Test
    public void shouldReturnEmptyListWhenNoPrivilegesExist() {
        when(privilegeManagementService.findPrivilegesWithPaginationAndSorting(5, 2, Direction.Ascending))
                .thenReturn(new PrivilegePageAndSortResponseDto(Collections.emptyList(), 5, 0, 4, 2, false, false));

        var result = privilegeManagementService.findPrivilegesWithPaginationAndSorting(5, 2, Direction.Ascending);

        assertTrue(result.getPrivilegeDtoList().isEmpty());
    }
    @Test
    public void shouldReturnCorrectPageWhenLastPageNotFull() {
        var lastPagePrivileges = List.of(new PrivilegeDto(3, "Edit Role"));
        var lastPageResponse = new PrivilegePageAndSortResponseDto(lastPagePrivileges, 2, 1, 4,2,false, true);

        when(privilegeManagementService.findPrivilegesWithPaginationAndSorting(2, 2, Direction.Ascending))
                .thenReturn(lastPageResponse);

        var result = privilegeManagementService.findPrivilegesWithPaginationAndSorting(2, 2,Direction.Ascending);

        assertEquals(1, result.getPrivilegeDtoList().size());
        assertEquals("Edit Role", result.getPrivilegeDtoList().get(0).getName());
    }
    @Test
    public void shouldCorrectlySortPrivilegesByName() {
        var sortedPrivileges = List.of(
                new PrivilegeDto(1, "Create Role"),
                new PrivilegeDto(2, "Edit Role"),
                new PrivilegeDto(3, "View Role"));
        var sortedResponse = new PrivilegePageAndSortResponseDto(sortedPrivileges, 0, 3, 4, 2, false, true);

        when(privilegeManagementService.findPrivilegesWithPaginationAndSorting(0, 3, Direction.Ascending))
                .thenReturn(sortedResponse);

        var result = privilegeManagementService.findPrivilegesWithPaginationAndSorting(0, 3, Direction.Ascending);

        assertEquals("Create Role", result.getPrivilegeDtoList().get(0).getName());
        assertEquals("Edit Role", result.getPrivilegeDtoList().get(1).getName());
        assertEquals("View Role", result.getPrivilegeDtoList().get(2).getName());
    }
}
