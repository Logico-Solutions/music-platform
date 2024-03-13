package org.logico.service;

import io.quarkus.security.ForbiddenException;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.logico.model.PrivilegeAssignmentType;
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

    public void checkPrivilege(String privilegeName) {
        String username = this.getUsername();
        boolean doesHavePrivilege = userRepository.findUserByUsername(username)
                .map(User::getRole)
                .map(Role::getPrivilegeAssignments)
                .map(privilegeAssignments -> privilegeAssignments.stream()
                        .anyMatch(pa -> pa.getPrivilege().getName().equals(privilegeName) &&
                                pa.getType().equals(PrivilegeAssignmentType.ALLOWED)))
                .orElse(false);

        if (!doesHavePrivilege) {
            throw new ForbiddenException(String.format("User doesn't have privilege %s", privilegeName));
        }
        log.infov("User has privilege {0}", privilegeName);
    }
}
