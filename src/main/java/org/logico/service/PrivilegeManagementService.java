package org.logico.service;

import io.quarkus.panache.common.Sort.Direction;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.logico.dto.response.PrivilegePageAndSortResponseDto;
import org.logico.mapper.PrivilegeMapper;
import org.logico.repository.PrivilegeRepository;

@JBossLog
@RequiredArgsConstructor
@ApplicationScoped
public class PrivilegeManagementService{

    private final PrivilegeRepository privilegeRepository;
    private final PrivilegeMapper privilegeMapper;

    public PrivilegePageAndSortResponseDto findPrivilegesWithPaginationAndSorting(Integer pageNumber, Integer rowCount, Direction direction){
        var totalRecords = privilegeRepository.count();

        var privileges = privilegeRepository.findPaginatedAndSortedById(pageNumber, rowCount, direction);

        var privilegeDTOs = privileges.stream()
                .map(privilegeMapper::privilegeToPrivilegeDto)
                .toList();

        var totalPages = (int) Math.ceil((double) totalRecords / (double) rowCount);

        return PrivilegePageAndSortResponseDto.builder()
                .privilegeDtoList(privilegeDTOs)
                .currentPage(pageNumber)
                .pageSize(rowCount)
                .totalRecords(totalRecords)
                .totalPages(totalPages)
                .hasNext(pageNumber < totalPages - 1)
                .hasPrevious(pageNumber > 0)
                .build();
    }
}
