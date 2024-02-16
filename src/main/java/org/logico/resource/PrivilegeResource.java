package org.logico.resource;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.logico.dto.PrivilegeDto;
import org.logico.service.PrivilegeManagementService;
import org.logico.util.Constants;

@Tag(name = "Privilege REST Endpoints")
@Path("/api/v1/privilege")
@JBossLog
@RequestScoped
@RequiredArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PrivilegeResource {

    private final PrivilegeManagementService privilegeManagementService;

    @GET
    @Path("/{id}")
    @Authenticated
    @Operation(summary = "Get privilege information by id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PrivilegeDto.class))})})
    public Response getPrivilegeById(@PathParam("id") @Min(0) Integer id) {
        return Response.ok()
                .entity(privilegeManagementService.getPrivilegeById(id))
                .build();
    }

}
