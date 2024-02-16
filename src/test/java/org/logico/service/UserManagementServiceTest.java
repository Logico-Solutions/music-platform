package org.logico.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.logico.dto.response.UserResponseDto;
import org.logico.mapper.UserMapper;
import org.logico.mapper.UserMapperImpl;
import org.logico.model.User;
import org.logico.repository.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserManagementServiceTest {

    @Mock
    private UserRepository userRepository;
    @Spy
    private UserMapper userMapper = new UserMapperImpl();
    @InjectMocks
    private UserManagementService userManagementService;

    @Test
    @DisplayName(value = "Getting user by non existing username")
    void getUserInfoByNonExistingUsername() {
        String username = "Non existing username";
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> userManagementService.getUserInfoByUsername(username));
    }

    @Test
    @DisplayName(value = "Getting user by valid username")
    void getUserInfoByValidUsername() {
        User user = User.builder()
                .id(1)
                .username("username")
                .email("user@gmail.com")
                .firstName("name")
                .lastName("surname")
                .fullName("surname name")
                .status("status")
                .build();
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
        UserResponseDto expected = userMapper.toDto(user);
        UserResponseDto actual = userManagementService.getUserInfoByUsername(user.getUsername());
        Assertions.assertEquals(expected, actual);
    }
}