package org.logico.resource;

import io.quarkus.security.Authenticated;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse.Status;
import org.logico.dto.response.UserResponseDto;
import org.logico.service.UserManagementService;
import org.logico.util.Constants;

@Tag(name = "User Management REST Endpoints")
@JBossLog
@AllArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/api/v1")
public class UserResource {

    private final UserManagementService userManagementService;

    @GET
    @Path("/{username}")
    @Authenticated
    @Operation(summary = "Get User data by username")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserResponseDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response getUserByUsername(String username) {
        try {
            return Response.ok()
                    .entity(userManagementService.getUserInfoByUsername(username)).build();
        } catch (Exception e) {
            log.warnv(e, "Failed!");
            return Response.status(Status.BAD_REQUEST).build();
        }
    }
}
