package org.logico.security.model.request;

import lombok.Builder;

@Builder
public record UserCredentials(String username,
                              String password) {
}
