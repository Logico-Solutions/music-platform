package org.logico.service;

import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.hibernate.query.SemanticException;
import org.logico.dto.response.RoleResponseDto;
import org.logico.mapper.RoleMapper;
import org.logico.model.Role;
import org.logico.repository.RoleRepository;
import org.logico.util.SortingDirections;

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
    public List<Role> getRolesPaginatedSorted(int pageIndex, int pageSize, String sortByColumn, Direction direction) {
        return roleRepository
                .findAll(Sort.by(sortByColumn).direction(direction))
                .page(pageIndex, pageSize)
                .list();
    }

    public Response validateGetRolesParams(int pageIndex, int pageSize, String sortByColumn, String direction) {
        final Response badRequestResponse = Response.status(Status.BAD_REQUEST).build();
        if (pageIndex < 0 || pageSize <= 0) {
            String warnMessage;
            if (pageIndex < 0) {
                warnMessage = String.format("Page index must be >=0 : %d", pageIndex);
            } else {
                warnMessage = String.format("Page size must be >0 : %d", pageSize);
            }
            log.warn(warnMessage);
            return badRequestResponse;
        }
        try {
            roleRepository.findAll(Sort.by(sortByColumn)).stream();
            SortingDirections.fromString(direction);
        } catch (IllegalArgumentException | SemanticException | UnsupportedOperationException e) {
            log.warnv("Illegal sorting parameters. Sort: {0}, Direction: {1}, Exception: {2}"
                    , sortByColumn, direction, e.getMessage());
            return badRequestResponse;
        }
        return null;
    }
}
