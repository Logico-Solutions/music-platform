package org.logico.resource;


import io.quarkus.security.Authenticated;
import io.quarkus.security.AuthenticationFailedException;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.logico.security.model.request.UserCredentials;
import org.logico.security.model.response.TokenResponse;
import org.logico.service.AuthenticationService;
import org.logico.service.JwtClaimService;
import org.logico.util.Constants;

import static java.lang.String.format;

@Tag(name = "Authentication REST Endpoints")
@Path("/api/v1")
@JBossLog
@RequestScoped
@AllArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    private final AuthenticationService authenticationService;
    private final JwtClaimService jwtClaimService;

    @POST
    @Path("/login")
    @PermitAll
    @Operation(summary = "Login operation")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TokenResponse.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response login(@RequestBody UserCredentials credentials) {
        try {
            log.infov("Receiving login request for user: {0}", credentials.username());
            return Response.ok()
                    .entity(authenticationService.validateAndAuthorizeUser(credentials))
                    .build();
        } catch (AuthenticationFailedException e) {
            log.warnv(e, "Login attempt failed for user: {0}", credentials.username());
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/logout")
    @Authenticated
    @Operation(summary = "Logout operation")
    @APIResponses(value = @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
            @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class))}))
    public Response logout() {
        authenticationService.logout(jwtClaimService.getUsername());
        return Response
                .ok(format("Refresh token for User %s was revoked successfully", jwtClaimService.getUsername()))
                .build();
    }
}
