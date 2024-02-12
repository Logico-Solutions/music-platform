package org.logico.service;

import org.logico.dto.response.PrivilegePageAndSortResponseDto;

public interface PrivilegeService {

    PrivilegePageAndSortResponseDto findPrivilegesWithPaginationAndSorting(Integer pageNumber, Integer rowCount);
}
