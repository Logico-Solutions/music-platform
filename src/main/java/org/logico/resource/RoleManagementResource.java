package org.logico.resource;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.logico.dto.response.RoleResponseDto;
import org.logico.model.Role;
import org.logico.service.JwtClaimService;
import org.logico.service.RoleManagementService;
import org.logico.util.Constants;
import org.logico.util.PrivilegeName;
import org.logico.util.RoleSortBy;

import java.util.List;

@Tag(name = "Role Management REST Endpoints")
@Path("/api/v1/roles")
@JBossLog
@RequestScoped
@AllArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
public class RoleManagementResource {

    private final RoleManagementService roleManagementService;
    private final JwtClaimService jwtClaimService;

    @GET
    @Authenticated
    @Operation(summary = "Getting list of Roles with pagination and sorting")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = RoleResponseDto.class))}),
            @APIResponse(responseCode = "204", description = "No content"),
            @APIResponse(responseCode = "400", description = "Illegal query parameter"),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "403", description = "Not privileged to view roles")})
    @Transactional
    public Response getRoles(@QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("10") int pageSize,
            @QueryParam("sort-by") @DefaultValue("name") String sortBy) {
        final String requiredPrivilege = PrivilegeName.VIEW_ROLE;
        if (jwtClaimService.hasPrivilege(requiredPrivilege)) {
            log.infov("User has privilege: {0}", requiredPrivilege);
        } else {
            log.warnv("User does not have privilege: {0}", requiredPrivilege);
            return Response.status(Status.FORBIDDEN).build();
        }
        ResponseBuilder paramsValidation = roleManagementService.validateGetRolesParams(pageIndex, pageSize, sortBy);
        if (paramsValidation != null) {
            return paramsValidation.build();
        }
        log.infov("Roles retrieved on page: {0}, with page size: {1}, and sorted by: {2}", pageIndex, pageSize, sortBy);
        List<Role> roles = roleManagementService.getRoles(pageIndex, pageSize);
        if (roles.isEmpty()) {
            log.infov("No elements on page: {0}", pageIndex);
            return Response
                    .status(Status.NO_CONTENT)
                    .build();
        }
        roles = roleManagementService.sortRoles(roles, RoleSortBy.fromName(sortBy));
        List<RoleResponseDto> dtoList = roleManagementService.mapRolesToDto(roles);
        return Response
                .ok(dtoList)
                .build();
    }
}
