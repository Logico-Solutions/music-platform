package org.logico.service;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.logico.model.Role;
import org.logico.model.User;
import org.logico.repository.UserRepository;

@JBossLog
@AllArgsConstructor
@ApplicationScoped
public class JwtClaimService {

    public static final String PRIVILEGES_CLAIM = "privileges";

    private final JsonWebToken jsonWebToken;
    private final SecurityIdentity securityIdentity;
    private final UserRepository userRepository;

    public String getUsername() {
        log.debug("Getting username from JWT");
        return jsonWebToken.getName();
    }

    public boolean hasPrivilege(String privilegeName) {
        String username = this.getUsername();
        return userRepository.findUserByUsername(username)
                .map(User::getRole)
                .map(Role::getPrivilegeAssignments)
                .map(privilegeAssignments -> privilegeAssignments.stream()
                        .anyMatch(pa -> pa.getPrivilege().getName().equals(privilegeName)))
                .orElse(false);
    }
}
