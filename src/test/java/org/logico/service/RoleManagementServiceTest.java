package org.logico.service;

import io.quarkus.panache.common.Sort.Direction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.junit.jupiter.api.Test;
import org.logico.dto.response.RoleResponseDto;
import org.logico.mapper.RoleMapper;
import org.logico.model.Role;
import org.logico.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@QuarkusTest
public class RoleManagementServiceTest {


    private final RoleRepository roleRepository = spy(RoleRepository.class);
    private final RoleMapper roleMapper = mock(RoleMapper.class);
    private final RoleManagementService roleManagementService = new RoleManagementService(roleRepository, roleMapper);

    @Test
    public void shouldMapRolesToDto() {
        List<RoleResponseDto> expected = new ArrayList<>();
        RoleResponseDto roleResponseDto1 = RoleResponseDto
                .builder()
                .id(1)
                .name("test1")
                .build();
        expected.add(roleResponseDto1);
        RoleResponseDto roleResponseDto2 = RoleResponseDto
                .builder()
                .id(2)
                .name("test2")
                .build();
        expected.add(roleResponseDto2);

        List<Role> roles = new ArrayList<>();
        Role role1 = Role
                .builder()
                .id(1)
                .name("test1")
                .build();
        roles.add(role1);
        Role role2 = Role
                .builder()
                .id(2)
                .name("test2")
                .build();
        roles.add(role2);

        when(roleMapper.toDto(role1)).thenReturn(roleResponseDto1);
        when(roleMapper.toDto(role2)).thenReturn(roleResponseDto2);

        List<RoleResponseDto> actual = roleManagementService.mapRolesToDto(roles);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnPaginatedAndSortedRoles() {
        Role expectedRole = roleRepository.findById(1);
        List<Role> expected = new ArrayList<>();
        expected.add(expectedRole);

        List<Role> actual = roleManagementService
                .getRolesPaginatedSorted(2, 1, "id", Direction.Descending);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnNullGetRolesQueryParamsValidation() {
        try (Response actual = roleManagementService
                .validateGetRolesParams(0, 1, "id", "ascending")) {
            assertNull(actual);
        }
    }

    @Test
    public void shouldReturnBadRequestGetRolesQueryParamsValidationWrongPage() {
        int expected = Response.status(Status.BAD_REQUEST).build().getStatus();
        int actual;
        try (Response response = roleManagementService
                .validateGetRolesParams(-1, 1, "id", "ascending")) {
            actual = response.getStatus();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnBadRequestGetRolesQueryParamsValidationWrongSize() {
        int expected = Response.status(Status.BAD_REQUEST).build().getStatus();
        int actual;
        try (Response response = roleManagementService
                .validateGetRolesParams(0, 0, "id", "ascending")) {
            actual = response.getStatus();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnBadRequestGetRolesQueryParamsValidationWrongSortBy() {
        int expected = Response.status(Status.BAD_REQUEST).build().getStatus();
        int actual;
        try (Response response = roleManagementService
                .validateGetRolesParams(0, 1, "test", "ascending")) {
            actual = response.getStatus();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnBadRequestGetRolesQueryParamsValidationWrongDirection() {
        int expected = Response.status(Status.BAD_REQUEST).build().getStatus();
        int actual;
        try (Response response = roleManagementService
                .validateGetRolesParams(0, 1, "id", "test")) {
            actual = response.getStatus();
        }
        assertEquals(expected, actual);
    }
}
