package org.logico.resource;

import io.quarkus.security.Authenticated;
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
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.logico.dto.response.RoleResponseDto;
import org.logico.mapper.RoleMapper;
import org.logico.model.Role;
import org.logico.repository.RoleRepository;
import org.logico.service.RoleManagementService;
import org.logico.util.Constants;
import org.logico.util.RoleSortBy;

import java.util.List;

@Tag(name = "Role Management REST Endpoints")
@Path("/api/v1/roles")
@JBossLog
@RequestScoped
@AllArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
public class RoleManagementResource {

    RoleManagementService roleManagementService;
    RoleMapper roleMapper;
    RoleRepository roleRepository;

    @GET
    @Authenticated
    @Operation(summary = "Getting list of Roles with pagination and sorting")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = RoleResponseDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "403", description = "Not privileged to view")})
    public Response getRoles(@QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("10") int pageSize,
            @QueryParam("sort-by") @DefaultValue("name") String sortBy) {
        //TODO: check privilege to view
        //TODO: unit test

        List<Role> sortedRoles = roleManagementService
                .getRolesSortedAndPaginated(pageIndex, pageSize, RoleSortBy.fromString(sortBy));
        List<RoleResponseDto> dtoList = roleManagementService.mapRolesToDto(sortedRoles);

        return Response
                .ok(dtoList)
                .build();
    }
}
