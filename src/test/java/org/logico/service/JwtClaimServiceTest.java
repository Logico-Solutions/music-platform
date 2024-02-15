package org.logico.service;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtClaimServiceTest {

    @Mock
    private JsonWebToken jsonWebToken;

    @InjectMocks
    private JwtClaimService jwtClaimService;

    @Test
    void testGetUsername() {
        when(jsonWebToken.getName()).thenReturn("johndoe");

        String username = jwtClaimService.getUsername();
        assertEquals("johndoe", username);
    }
}
