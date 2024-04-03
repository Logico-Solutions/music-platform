package org.logico.service;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.logico.repository.TrackingUserRepository;

@RequiredArgsConstructor
@ApplicationScoped
public class TrackingUserService {

    private final JwtClaimService jwtClaimService;
    private final TrackingUserRepository trackingUserRepository;


}
