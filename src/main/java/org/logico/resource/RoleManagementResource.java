package org.logico.resource;

import org.logico.service.RoleManagementService;
import org.logico.model.Role;

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
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
@Tag(name = "Role Management REST Endpoints")
@Path("/api/v1/roles")
@JBossLog
@RequestScoped
@AllArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class RoleManagementResource {

    @Inject
    RoleManagementService roleManagementService;

    @GET
    @Path("/{id}")
    public Role getRoleById(@PathParam("id") Integer id) {
        return roleManagementService.findById(id);
    }
}
