package org.logico.mapper;

import org.logico.dto.response.RoleResponseDto;
import org.logico.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta", uses = {PrivilegeAssignmentMapperUtils.class})
public interface RoleMapper {

    @Mapping(source = "privilegeAssignments", target = "privileges")
    RoleResponseDto toDto(Role role);

}