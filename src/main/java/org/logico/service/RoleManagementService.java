package org.logico.service;

import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
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
    public List<Role> getRolesPaginatedSorted(int pageIndex, int pageSize, String sortBy, Direction direction) {
        return roleRepository
                .findAll(Sort.by(sortBy).direction(direction))
                .page(pageIndex, pageSize)
                .list();
    }

    public void validateGetRolesParams(String sortBy, String direction) {
        try {
            roleRepository.findAll(Sort.by(sortBy)).page(0, 1).stream();
            SortingDirections.fromString(direction);
        } catch (IllegalArgumentException | SemanticException | UnsupportedOperationException e) {
            log.warnv(
                    "Illegal sorting parameters. Sort: {0}, Direction: {1}, Exception: {2}",
                    sortBy, direction, e.getMessage());
            throw new BadRequestException(
                    String.format("Illegal sorting parameters. Sort: %s, Direction: %s", sortBy, direction));
        }
    }
}
