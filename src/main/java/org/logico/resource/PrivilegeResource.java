package org.logico.resource;

import io.quarkus.panache.common.Sort.Direction;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.logico.dto.error.ApiError;
import org.logico.dto.response.PrivilegePageAndSortResponseDto;
import org.logico.service.PrivilegeManagementService;

import static org.logico.util.Constants.BAD_REQUEST;
import static org.logico.util.Constants.NOT_FOUND_ERROR;
import static org.logico.util.Constants.SERVER_ERROR;
import static org.logico.util.Constants.SUCCESSFULLY_RETRIEVED;

@Tag(name = "Managing privileges REST Endpoints")
@Path("/api/v1/privileges")
@JBossLog
@ApplicationScoped
@RequiredArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PrivilegeResource {

    private final PrivilegeManagementService privilegeManagementService;

    @GET
    @Path("/list/paginated-sorted")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = SUCCESSFULLY_RETRIEVED, content = {
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PrivilegePageAndSortResponseDto.class))}),
            @APIResponse(responseCode = "500", description = SERVER_ERROR, content = {
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ApiError.class)
                    )
            }),
            @APIResponse(responseCode = "400", description = BAD_REQUEST, content = {
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ApiError.class)
                    )
            }),
            @APIResponse(responseCode = "404", description = NOT_FOUND_ERROR, content = {
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ApiError.class)
                    )
            })
    })
    @PermitAll
    public Response getPrivilegesWithPaginationAndSorting(@QueryParam("pageNumber") @NotNull @Min(0) Integer pageNumber,
            @QueryParam("rowCount") @NotNull @Min(1) Integer rowCount, @QueryParam("direction") @NotNull Direction direction){
          log.infov("Received request for privilege listing with pagination. Page number: {0}, Row count: {1}, Direction : {2}",
                  pageNumber, rowCount, direction);
          return Response.ok()
                  .entity(privilegeManagementService.findPrivilegesWithPaginationAndSorting(pageNumber, rowCount, direction))
                  .build();
    }
}
