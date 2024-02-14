package org.logico.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;
import org.junit.jupiter.api.Test;
import org.logico.dto.response.RoleResponseDto;
import org.logico.mapper.RoleMapper;
import org.logico.model.PrivilegeAssignment;
import org.logico.model.Role;
import org.logico.model.User;
import org.logico.repository.RoleRepository;
import org.logico.util.RoleSortBy;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public void shouldReturnPaginatedRoles() {
        Role expectedRole = roleRepository.findById(2);
        List<Role> expected = new ArrayList<>();
        expected.add(expectedRole);

        List<Role> actual = roleManagementService.getRoles(1, 1);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSortRoles() {
        List<Role> expected = new ArrayList<>();
        Role role1 = Role
                .builder()
                .id(1)
                .name("testB")
                .build();
        Role role2 = Role
                .builder()
                .id(2)
                .name("testA")
                .build();
        expected.add(role2);
        expected.add(role1);

        List<Role> roles = new ArrayList<>();
        roles.add(role1);
        roles.add(role2);

        List<Role> actual = roleManagementService.sortRoles(roles, RoleSortBy.NAME);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldCompareRoleCreatedAt() {
        Role role1 = Role
                .builder()
                .id(1)
                .name("test1")
                .createdAt(Instant.parse("2024-01-01T00:00:00Z"))
                .build();
        Role role2 = Role
                .builder()
                .id(2)
                .name("test2")
                .createdAt(Instant.parse("2024-01-02T00:00:00Z"))
                .build();

        Comparator<Role> actual = roleManagementService.getRoleComparator(RoleSortBy.CREATED_AT);
        assertEquals(-1, actual.compare(role1, role2));
    }

    @Test
    public void shouldCompareRoleId() {
        Role role1 = Role
                .builder()
                .id(1)
                .name("test1")
                .build();
        Role role2 = Role
                .builder()
                .id(2)
                .name("test2")
                .build();

        Comparator<Role> actual = roleManagementService.getRoleComparator(RoleSortBy.ID);
        assertEquals(-1, actual.compare(role1, role2));
    }

    @Test
    public void shouldCompareRoleName() {
        Role role1 = Role
                .builder()
                .id(1)
                .name("test1")
                .build();
        Role role2 = Role
                .builder()
                .id(2)
                .name("test2")
                .build();

        Comparator<Role> actual = roleManagementService.getRoleComparator(RoleSortBy.NAME);
        assertEquals(-1, actual.compare(role1, role2));
    }

    @Test
    public void shouldCompareRoleUsers() {
        Set<User> users = new HashSet<>();
        users.add(User
                .builder()
                .id(1)
                .build());
        Role role1 = Role
                .builder()
                .id(1)
                .name("test1")
                .users(users)
                .build();
        Role role2 = Role
                .builder()
                .id(2)
                .name("test2")
                .build();

        Comparator<Role> actual = roleManagementService.getRoleComparator(RoleSortBy.USERS);
        assertEquals(-1, actual.compare(role1, role2));
    }

    @Test
    public void shouldCompareRolePrivileges() {
        Set<PrivilegeAssignment> privilegeAssignments = new HashSet<>();
        privilegeAssignments.add(new PrivilegeAssignment());
        Role role1 = Role
                .builder()
                .id(1)
                .name("test1")
                .privilegeAssignments(privilegeAssignments)
                .build();
        Role role2 = Role
                .builder()
                .id(2)
                .name("test2")
                .build();

        Comparator<Role> actual = roleManagementService.getRoleComparator(RoleSortBy.PRIVILEGE_ASSIGNMENTS);
        assertEquals(-1, actual.compare(role1, role2));
    }

    @Test
    public void shouldReturnNullGetRolesQueryParamsValidation() {
        ResponseBuilder actual = roleManagementService.validateGetRolesParams(0, 1, RoleSortBy.ID.name());
        assertNull(actual);
    }

    @Test
    public void shouldReturnBadRequestGetRolesQueryParamsValidation() {
        int expected = Response.status(Status.BAD_REQUEST).build().getStatus();
        int actual = roleManagementService.validateGetRolesParams(-1, 1, RoleSortBy.ID.name())
                .build().getStatus();
        assertEquals(expected, actual);
    }
}
