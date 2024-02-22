@Tag(name = "Role Management REST Endpoints")
@Path("/api/v1/roles")
@JBossLog
@RequestScoped
@AllArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RoleManagementResource {

    @Inject
    RoleService roleService;

    @GET
    @Path("/{id}")
    public Role getRoleById(@PathParam("id") Long id) {
        return roleService.findById(id);
    }
}
