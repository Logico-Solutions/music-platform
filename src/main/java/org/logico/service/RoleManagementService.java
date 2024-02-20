package org.logico.service;

import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.logico.dto.response.RoleResponseDto;
import org.logico.mapper.RoleMapperUtils;
import org.logico.model.Role;
import org.logico.repository.RoleRepository;
import org.logico.util.SortingDirections;

import java.util.List;

@JBossLog
@RequiredArgsConstructor
@ApplicationScoped
@Transactional
public class RoleManagementService {

    private final RoleRepository roleRepository;
    private final RoleMapperUtils roleMapperUtils;

    public List<Role> getRolesPaginatedSorted(int pageIndex, int pageSize, String sortBy, Direction direction) {
        return roleRepository
                .findAll(Sort.by(sortBy).direction(direction))
                .page(pageIndex, pageSize)
                .list();
    }

    public List<RoleResponseDto> getDtoRolesPaginatedSorted(int pageIndex, int pageSize,
            String sortBy, String direction) {
        List<Role> roles = this.getRolesPaginatedSorted(pageIndex, pageSize,
                sortBy, SortingDirections.fromString(direction));
        log.infov("Roles retrieved on page: {0}, with page size: {1}, sorted by: {2}, direction: {3}",
                pageIndex, pageSize, sortBy, direction);
        return roleMapperUtils.toDto(roles);
    }
}
