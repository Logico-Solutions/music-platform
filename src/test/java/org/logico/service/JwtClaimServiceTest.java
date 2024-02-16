package org.logico.service;

import io.quarkus.security.ForbiddenException;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.logico.model.Privilege;
import org.logico.model.PrivilegeAssignment;
import org.logico.model.PrivilegeAssignmentType;
import org.logico.model.Role;
import org.logico.model.User;
import org.logico.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
public class JwtClaimServiceTest {

    private final JsonWebToken jsonWebToken = mock(JsonWebToken.class);
    private final SecurityIdentity securityIdentity = mock(SecurityIdentity.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final JwtClaimService jwtClaimService = new JwtClaimService(jsonWebToken, securityIdentity, userRepository);
    private User user;

    @BeforeEach
    public void setupUserAndMocks() {
        user = User
                .builder()
                .username("user")
                .build();

        when(jsonWebToken.getName()).thenReturn(user.getUsername());
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
    }

    @Test
    public void shouldNotThrowExceptionCheckPrivilege() {
        Role role = new Role();
        Privilege privilege = Privilege
                .builder()
                .name("test")
                .build();
        PrivilegeAssignment privilegeAssignment = PrivilegeAssignment
                .builder()
                .role(role)
                .privilege(privilege)
                .type(PrivilegeAssignmentType.ALLOWED)
                .build();
        Set<PrivilegeAssignment> privilegeAssignments = new HashSet<>();
        privilegeAssignments.add(privilegeAssignment);
        role.setPrivilegeAssignments(privilegeAssignments);
        user.setRole(role);

        assertDoesNotThrow(() -> jwtClaimService.checkPrivilege("test"));
    }

    @Test
    public void shouldReturnFalseOnHavingPrivilege() {
        String privilegeName = "test";

        ForbiddenException exception = assertThrows(ForbiddenException.class,
                () -> jwtClaimService.checkPrivilege(privilegeName));
        assertEquals(String.format("User doesn't have privilege %s", privilegeName), exception.getMessage());
    }
}
