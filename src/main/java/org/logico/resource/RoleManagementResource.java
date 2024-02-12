package org.logico.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.logico.dto.response.RoleResponseDto;
import org.logico.util.Constants;

@Tag(name = "Role Management REST Endpoints")
@Path("/api/v1/roles")
@JBossLog
@RequestScoped
@AllArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
public class RoleManagementResource {

    @GET
    @PermitAll
    @Operation(summary = "Getting list of Roles with pagination and sorting")
    @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
            @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RoleResponseDto.class))})
    public Response getRoles(@QueryParam("page") @DefaultValue("0") int page) {
        return Response
                .ok("Roles retrieved successfully")
                .build();
    }
}
