package org.logico.security.model.response;

public record TokenResponse(String accessToken, String refreshToken) {
}
