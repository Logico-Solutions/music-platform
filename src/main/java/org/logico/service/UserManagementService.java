package org.logico.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.logico.dto.response.UserResponseDto;
import org.logico.mapper.UserMapper;
import org.logico.model.User;
import org.logico.repository.UserRepository;

@JBossLog
@RequiredArgsConstructor
@ApplicationScoped
public class UserManagementService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserResponseDto getUserInfoByUsername(String username) {
        User userFromDB = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user by username: " + username));
        return userMapper.toDto(userFromDB);
    }
}
