package org.logico.resource;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
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
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.logico.dto.response.UserResponseDto;
import org.logico.service.JwtClaimService;
import org.logico.service.UserManagementService;
import org.logico.util.Constants;

@Tag(name = "User management Endpoints")
@Path("/api/v1")
@JBossLog
@RequestScoped
@AllArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserManagementService userManagementService;
    private final JwtClaimService jwtClaimService;

    @GET
    @Path("/me")
    @Authenticated
    @Operation(summary = "Get user info from JWT")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserResponseDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response getUser() {
        try {
            return Response.ok()
                    .entity(userManagementService.getUserInfoByUsername(jwtClaimService.getUsername()))
                    .build();
        } catch (Exception e) {
            log.warnv(e, "Getting user info attempt failed");
            return Response.status(Status.BAD_REQUEST).build();
        }
    }
}
