package org.logico.resource;

import org.logico.service.RoleManagementService;
import org.logico.model.Role;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import static java.lang.String.format;
@Tag(name = "Role Management REST Endpoints")
@Path("/api/v1/roles")
@JBossLog
@RequestScoped
@AllArgsConstructor
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
