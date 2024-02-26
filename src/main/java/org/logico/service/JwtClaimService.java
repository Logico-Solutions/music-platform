package org.logico.service;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.jwt.JsonWebToken;

@JBossLog
@AllArgsConstructor
@ApplicationScoped
public class JwtClaimService {

    public static final String PRIVILEGES_CLAIM = "privileges";

    private final JsonWebToken jsonWebToken;
    private final SecurityIdentity securityIdentity;

    public String getUsername() {
        log.debug("Getting username from JWT");
        return jsonWebToken.getName();
    }
}
