package org.logico.mapper;

import jakarta.inject.Inject;
import org.logico.dto.PrivilegeAssignmentDto;
import org.logico.model.PrivilegeAssignment;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "jakarta")
public class PrivilegeAssignmentMapperUtils {

    @Inject
    PrivilegeAssignmentMapper privilegeAssignmentMapper;

    public List<PrivilegeAssignmentDto> toDto(Set<PrivilegeAssignment> privilegeAssignments) {
        return privilegeAssignments.stream()
                .map(privilegeAssignmentMapper::toDto)
                .toList();
    }
}
