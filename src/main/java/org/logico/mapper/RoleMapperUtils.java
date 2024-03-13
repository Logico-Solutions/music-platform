package org.logico.mapper;

import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.logico.dto.response.RoleResponseDto;
import org.logico.model.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "jakarta")
@RequiredArgsConstructor
@AllArgsConstructor
public class RoleMapperUtils {

    @Inject
    RoleMapper roleMapper;

    public List<RoleResponseDto> toDto(List<Role> roles) {
        return roles.stream()
                .map(roleMapper::toDto)
                .toList();
    }
}
