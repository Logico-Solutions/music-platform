package org.logico.service;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.logico.dto.response.RoleResponseDto;
import org.logico.mapper.RoleMapper;
import org.logico.model.AuditEntity;
import org.logico.model.Role;
import org.logico.repository.RoleRepository;
import org.logico.util.RoleSortBy;

import java.util.Comparator;
import java.util.List;

@JBossLog
@RequiredArgsConstructor
@ApplicationScoped
public class RoleManagementService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public List<Role> getRolesSortedAndPaginated(int pageIndex, int pageSize, RoleSortBy sortBy) {
        return roleRepository
                .findAll()
                .page(pageIndex, pageSize)
                .stream()
                .sorted(getRoleComparator(sortBy))
                .toList();
    }

    public List<RoleResponseDto> mapRolesToDto(List<Role> roles) {
        return roles
                .stream()
                .map(roleMapper::toDto)
                .toList();
    }

    public Comparator<Role> getRoleComparator(RoleSortBy sortBy) {
        return switch (sortBy) {
            case CREATED_AT -> Comparator.comparing(AuditEntity::getCreatedAt);
            case ID -> Comparator.comparing(Role::getId);
            case NAME -> Comparator.comparing(Role::getName);
            case USERS -> Comparator.comparing(r -> -1 * r.getUsers().size());
            case PRIVILEGE_ASSIGNMENTS -> Comparator.comparing(r -> -1 * r.getPrivilegeAssignments().size());
        };
    }
}
