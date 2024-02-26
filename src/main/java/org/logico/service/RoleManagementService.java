package org.logico.service;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.logico.mapper.RoleMapper;
import org.logico.repository.RoleRepository;
import org.logico.model.Role;

@JBossLog
@RequiredArgsConstructor
@ApplicationScoped
public class RoleManagementService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public Role findById(Integer id) {
        return roleRepository.findById(id);
    }

}
