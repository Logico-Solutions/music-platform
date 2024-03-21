package org.logico.service;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.logico.dto.PrivilegeDto;
import org.logico.exhandler.PrivilegeNotFoundException;
import org.logico.mapper.PrivilegeMapper;
import org.logico.repository.PrivilegeRepository;

@JBossLog
@ApplicationScoped
@RequiredArgsConstructor
public class PrivilegeManagementService {

    private final PrivilegeRepository privilegeRepository;
    private final PrivilegeMapper mapper;

    public PrivilegeDto getPrivilegeById(Integer id) {
        log.debugf("getPrivilegeById() - start: id = %d", id);
        var byId = privilegeRepository.findById(id);
        if (byId == null) {
            throw new PrivilegeNotFoundException("Privilege with id " + id + " was not found");
        }
        log.debugf("getPrivilegeById() -> toDto - start");
        var dto = mapper.privilegeToPrivilegeDto(byId);
        log.debugf("getPrivilegeById() - end");
        return dto;
    }
}
