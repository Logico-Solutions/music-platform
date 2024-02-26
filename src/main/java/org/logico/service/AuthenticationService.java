package org.logico.service;

import io.quarkus.security.AuthenticationFailedException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.logico.dto.response.RoleResponseDto;
import org.logico.mapper.RoleMapper;
import org.logico.model.User;
import org.logico.repository.UserRepository;
import org.logico.security.JsonWebTokenService;
import org.logico.security.model.request.UserCredentials;
import org.logico.security.model.response.TokenResponse;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Optional;

@JBossLog
@ApplicationScoped
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleMapper roleMapper;
    private final JsonWebTokenService tokenService;

    private final String secret;
    private final Integer iteration;
    private final Integer keyLength;

    public AuthenticationService(@ConfigProperty(name = "app.password.secret") String secret,
            @ConfigProperty(name = "app.password.iteration") Integer iteration,
            @ConfigProperty(name = "app.password.length") Integer keyLength,
            UserRepository userRepository,
            RoleMapper roleMapper,
            JsonWebTokenService tokenService) {
        this.secret = secret;
        this.iteration = iteration;
        this.keyLength = keyLength;
        this.userRepository = userRepository;
        this.roleMapper = roleMapper;
        this.tokenService = tokenService;
    }

    public TokenResponse validateAndAuthorizeUser(UserCredentials credentials) {
        boolean isAuthenticated = validateCredentials(credentials.username(), credentials.password());
        if (isAuthenticated) {
            return generateTokenFor(credentials.username());
        } else {
            throw new AuthenticationFailedException("Invalid username or password");
        }
    }

    public TokenResponse validateAndRefreshToken(String refreshToken) {
        String username = tokenService.validateAndGetUsername(refreshToken);
        return generateTokenFor(username);
    }

    public void logout(String username) {
        tokenService.logout(username);

    }

    private TokenResponse generateTokenFor(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new AuthenticationFailedException("Invalid username or password"));

        Optional<RoleResponseDto> role = Optional.ofNullable(user.getRole()).map(roleMapper::toDto);
        if (role.isEmpty()) {
            throw new AuthenticationFailedException(
                    "Insufficient permissions to login. Please contact your system administrator for assistance.");
        }

        return tokenService.generateToken(user, role.get());
    }

    public boolean validateCredentials(String username, String password) {
        User userEntity = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new AuthenticationFailedException("Invalid username or password"));
        try {
            return userEntity.getPassword().equals(encode(password));
        } catch (Exception e) {
            throw new AuthenticationFailedException("Invalid username or password");
        }
    }

    public String encode(CharSequence cs) {
        try {
            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(
                            new PBEKeySpec(cs.toString().toCharArray(), secret.getBytes(), iteration, keyLength))
                    .getEncoded();
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }
}