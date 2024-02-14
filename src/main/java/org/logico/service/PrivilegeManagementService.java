package org.logico.service;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.logico.dto.response.PrivilegePageAndSortResponseDto;
import org.logico.mapper.PrivilegeMapper;
import org.logico.repository.PrivilegeRepository;

@JBossLog
@RequiredArgsConstructor
@ApplicationScoped
public class PrivilegeManagementService implements PrivilegeService{

    private final PrivilegeRepository privilegeRepository;
    private final PrivilegeMapper privilegeMapper;

    public PrivilegePageAndSortResponseDto findPrivilegesWithPaginationAndSorting(Integer pageNumber, Integer rowCount){
        var privileges = privilegeRepository.findPaginatedAndSortedById(pageNumber, rowCount);

        var privilegeDTOs = privileges.stream()
                .map(privilegeMapper::privilegeToPrivilegeDto)
                .toList();

        return PrivilegePageAndSortResponseDto.builder()
                .privilegeDtoList(privilegeDTOs)
                .build();
    }
}
