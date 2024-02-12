package org.logico.security;

import io.quarkus.security.AuthenticationFailedException;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jose4j.jwt.NumericDate;
import org.logico.dto.response.RoleResponseDto;
import org.logico.model.User;
import org.logico.security.model.response.TokenResponse;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;

@ApplicationScoped
public class JsonWebTokenService {

    public static final String ROLE_ID = "role_id";

    private final String tokenTTL;
    private final String refreshTokenTTL;
    private final String secret;
    private final JWTParser parser;
    private final Map<String, String> usernameToActiveRefreshTokenJtiMap = new ConcurrentHashMap<>();
    private final Map<String, ScheduledFuture<?>> removalTasks = new ConcurrentHashMap<>();
    private final Map<String, String> usernameToActiveTokenJtiMap = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    public JsonWebTokenService(@ConfigProperty(name = "app.token.ttl") String tokenTTL,
            @ConfigProperty(name = "app.token.refresh.ttl") String refreshTokenTTL,
            @ConfigProperty(name = "app.token.refresh.secret") String secret,
            JWTParser parser) {
        this.tokenTTL = tokenTTL;
        this.refreshTokenTTL = refreshTokenTTL;
        this.secret = secret;
        this.parser = parser;
    }

    public TokenResponse generateToken(User user, RoleResponseDto role) {
        String jti = UUID.randomUUID().toString();
        var accessToken = Jwt.subject(user.getEmail())
                .upn(user.getUsername())
                .expiresAt(Instant.now().plus(Duration.parse(tokenTTL)))
                .claim(Claims.full_name, user.getFullName())
                .claim(Claims.jti, jti)
                .claim(ROLE_ID, role.getId())
                .innerSign()
                .encrypt();
        usernameToActiveTokenJtiMap.put(user.getUsername(), jti);
        return new TokenResponse(accessToken, null);
    }

    private String generateRefreshToken(User user) {
        String jti = UUID.randomUUID().toString();
        String username = user.getUsername();
        invalidateRefreshToken(username);
        storeValueAndScheduleRemoving(username, jti);

        return Jwt.claims()
                .upn(username)
                .claim(Claims.jti, jti)
                .expiresAt(Instant.now().plus(Duration.parse(refreshTokenTTL)))
                .jws()
                .innerSignWithSecret(secret)
                .encrypt();
    }

    public String validateAndGetUsername(String token) {
        try {
            JsonWebToken jwt = parser.verify(token, secret);
            String jti = jwt.getTokenID();
            String username = jwt.getName();
            if (!usernameToActiveRefreshTokenJtiMap.containsKey(username) ||
                    !usernameToActiveRefreshTokenJtiMap.get(username).equals(jti)) {
                throw new AuthenticationFailedException("Token has already been updated");
            }
            if (!NumericDate.now().isBefore(NumericDate.fromSeconds(jwt.getExpirationTime()))) {
                invalidateRefreshToken(username);
                throw new AuthenticationFailedException("Token has been expired");
            }

            logout(username);
            return username;
        } catch (ParseException e) {
            throw new AuthenticationFailedException(e);
        }
    }

    public void invalidateRefreshToken(String username) {
        String existingJti = usernameToActiveRefreshTokenJtiMap.get(username);
        if (isNull(existingJti)) {
            return;
        }
        ScheduledFuture<?> existingTask = removalTasks.get(existingJti);
        if (existingTask != null) {
            existingTask.cancel(false);
            removalTasks.remove(existingJti);
        }
        usernameToActiveRefreshTokenJtiMap.remove(username);
    }

    private void storeValueAndScheduleRemoving(String username, String jti) {
        usernameToActiveRefreshTokenJtiMap.put(username, jti);

        ScheduledFuture<?> removalTask = scheduler.schedule(() -> {
            usernameToActiveRefreshTokenJtiMap.remove(username);
            removalTasks.remove(jti);
        }, Duration.parse(refreshTokenTTL).toMillis(), TimeUnit.MILLISECONDS);

        removalTasks.put(jti, removalTask);
    }

    private void invalidateAccessToken(String username) {
        if (!usernameToActiveTokenJtiMap.containsKey(username)) {
            throw new AuthenticationFailedException("Token is not valid");
        }
        usernameToActiveTokenJtiMap.remove(username);
    }

    public void logout(String username) {
        invalidateRefreshToken(username);
        invalidateAccessToken(username);
    }

    public boolean isTokenActive(String jti, String username) {
        return usernameToActiveTokenJtiMap.containsKey(username) &&
                usernameToActiveTokenJtiMap.get(username).equals(jti);
    }
}
