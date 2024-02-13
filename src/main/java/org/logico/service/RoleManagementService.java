package org.logico.service;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.logico.dto.response.RoleResponseDto;
import org.logico.mapper.RoleMapper;
import org.logico.model.Role;
import org.logico.repository.RoleRepository;

import java.util.Comparator;
import java.util.List;

@JBossLog
@RequiredArgsConstructor
@ApplicationScoped
public class RoleManagementService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public List<Role> getSortedRoles(int pageIndex, int pageSize) {
        return roleRepository
                .findAll()
                .page(pageIndex, pageSize)
                .stream()
                .sorted(Comparator.comparing(Role::getName))
                .toList();
    }

}
