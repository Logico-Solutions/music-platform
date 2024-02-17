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
import org.logico.mapper.RoleMapperUtils;
import org.logico.model.Role;
import org.logico.repository.RoleRepository;
import org.logico.util.SortingDirections;

import java.util.List;

@JBossLog
@RequiredArgsConstructor
@ApplicationScoped
public class RoleManagementService {

    private final RoleRepository roleRepository;
    private final RoleMapperUtils roleMapperUtils;

    @Transactional
    public List<Role> getRolesPaginatedSorted(int pageIndex, int pageSize, String sortBy, Direction direction) {
        return roleRepository
                .findAll(Sort.by(sortBy).direction(direction))
                .page(pageIndex, pageSize)
                .list();
    }

    public List<RoleResponseDto> getDtoRolesPaginatedSorted(int pageIndex, int pageSize, String sortBy,
            String direction) {
        this.validateGetRolesParams(sortBy, direction);
        List<Role> roles = this.getRolesPaginatedSorted(pageIndex, pageSize,
                sortBy, SortingDirections.fromString(direction));
        log.infov("Roles retrieved on page: {0}, with page size: {1}, sorted by: {2}, direction: {3}",
                pageIndex, pageSize, sortBy, direction);
        return roleMapperUtils.toDto(roles);
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
