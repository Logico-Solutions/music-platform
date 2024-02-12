package org.logico.mapper;

import org.logico.dto.PrivilegeAssignmentDto;
import org.logico.model.PrivilegeAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta")
public interface PrivilegeAssignmentMapper {

    @Mapping(source = "privilege.id", target = "id")
    @Mapping(source = "privilege.name", target = "name")
    PrivilegeAssignmentDto toDto(PrivilegeAssignment privilegeAssignment);
}
