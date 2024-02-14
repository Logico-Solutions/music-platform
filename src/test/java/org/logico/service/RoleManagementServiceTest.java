package org.logico.service;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.logico.mapper.RoleMapper;
import org.logico.model.Role;
import org.logico.repository.RoleRepository;
import org.logico.util.RoleSortBy;

import java.time.Instant;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@QuarkusTest
public class RoleManagementServiceTest {


    private final RoleRepository roleRepository = mock(RoleRepository.class);
    private final RoleMapper roleMapper = mock(RoleMapper.class);
    private final RoleManagementService roleManagementService = new RoleManagementService(roleRepository, roleMapper);

    @Test
    public void testGetRoleComparatorCreatedAt() {
        Comparator<Role> comparator = roleManagementService.getRoleComparator(RoleSortBy.CREATED_AT);
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("Role 1");
        role1.setCreatedAt(Instant.parse("2022-01-01T10:30:00Z"));

        Role role2 = new Role();
        role2.setId(2);
        role2.setName("Role 2");
        role2.setCreatedAt(Instant.parse("2022-01-02T10:30:00Z"));

        assertEquals(-1, comparator.compare(role1, role2));
    }
}
