package org.logico.service;

import io.quarkus.panache.common.Sort.Direction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.logico.dto.response.RoleResponseDto;
import org.logico.mapper.RoleMapperUtils;
import org.logico.model.Role;
import org.logico.repository.RoleRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@QuarkusTest
public class RoleManagementServiceTest {

    private final RoleRepository roleRepository = spy(RoleRepository.class);
    private final RoleMapperUtils roleMapperUtils = mock(RoleMapperUtils.class);
    private final RoleManagementService roleManagementService = new RoleManagementService(roleRepository,
                                                                                        roleMapperUtils);

    @Test
    public void shouldReturnPaginatedSortedRoles() {
        Role expectedRole = roleRepository.findById(1);
        List<Role> expected = List.of(expectedRole);

        List<Role> actual = roleManagementService
                .getRolesPaginatedSorted(2, 1, "id", Direction.Descending);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnPaginatedSortedDtoRoles() {
        Role expectedRole = roleRepository.findById(1);
        List<Role> roles = List.of(expectedRole);
        List<RoleResponseDto> expected = List.of(RoleResponseDto.builder()
                .id(expectedRole.getId())
                .build());
        when(roleMapperUtils.toDto(roles)).thenReturn(expected);

        List<RoleResponseDto> actual = roleManagementService
                .getDtoRolesPaginatedSorted(2, 1, "id", "descending");
        assertEquals(expected, actual);
    }
}
