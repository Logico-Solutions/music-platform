package org.logico.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;
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

    public List<RoleResponseDto> mapRolesToDto(List<Role> roles) {
        return roles
                .stream()
                .map(roleMapper::toDto)
                .toList();
    }

    @Transactional
    public List<Role> getRoles(int pageIndex, int pageSize) {
        return roleRepository
                .findAll()
                .page(pageIndex, pageSize)
                .list();
    }

    public List<Role> sortRoles(List<Role> roles, RoleSortBy sortBy) {
        return roles
                .stream()
                .sorted(getRoleComparator(sortBy))
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

    public ResponseBuilder validateGetRolesParams(int pageIndex, int pageSize, String sortBy) {
        final ResponseBuilder badRequestResponse = Response.status(Status.BAD_REQUEST);
        if (pageIndex < 0) {
            log.warnv("Page index must be >=0 : {0}", pageIndex);
            return badRequestResponse;
        }
        if (pageSize <= 0) {
            log.warnv("Page index must be >=0 : {0}", pageSize);
            return badRequestResponse;
        }
        try {
            RoleSortBy.fromString(sortBy);
        } catch (IllegalArgumentException e) {
            log.warnv(e, "Illegal sort-by parameter: {0}", sortBy);
            return badRequestResponse;
        }
        return null;
    }
}
