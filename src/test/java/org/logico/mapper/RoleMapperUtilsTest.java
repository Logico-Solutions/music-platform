package org.logico.mapper;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.logico.dto.response.RoleResponseDto;
import org.logico.model.Role;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
public class RoleMapperUtilsTest {

    private final RoleMapper roleMapper = mock(RoleMapper.class);
    private final RoleMapperUtils roleMapperUtils = new RoleMapperUtils(roleMapper);

    @Test
    public void shouldMapRolesToDto() {
        RoleResponseDto roleResponseDto1 = RoleResponseDto
                .builder()
                .id(1)
                .name("test1")
                .build();
        RoleResponseDto roleResponseDto2 = RoleResponseDto
                .builder()
                .id(2)
                .name("test2")
                .build();
        List<RoleResponseDto> expected = List.of(roleResponseDto1, roleResponseDto2);

        Role role1 = Role
                .builder()
                .id(1)
                .name("test1")
                .build();
        Role role2 = Role
                .builder()
                .id(2)
                .name("test2")
                .build();
        List<Role> roles = List.of(role1, role2);

        when(roleMapper.toDto(role1)).thenReturn(roleResponseDto1);
        when(roleMapper.toDto(role2)).thenReturn(roleResponseDto2);

        List<RoleResponseDto> actual = roleMapperUtils.toDto(roles);
        assertEquals(expected, actual);
    }
}
