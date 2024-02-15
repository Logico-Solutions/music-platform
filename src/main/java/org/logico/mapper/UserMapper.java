package org.logico.mapper;

import org.logico.dto.response.UserResponseDto;
import org.logico.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta")
public interface UserMapper {

    @Mapping(source = "role.name", target = "role")
    UserResponseDto toDto(User user);

}