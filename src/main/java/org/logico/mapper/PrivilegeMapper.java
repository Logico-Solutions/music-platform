package org.logico.mapper;

import org.logico.dto.PrivilegeDto;
import org.logico.model.Privilege;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta")
public interface PrivilegeMapper {

    PrivilegeDto privilegeToPrivilegeDto(Privilege privilege);

}
