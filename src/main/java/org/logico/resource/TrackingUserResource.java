package org.logico.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.logico.dto.TrackingUserDto;
import org.logico.model.TrackingUser;
import org.logico.service.TrackingUserService;
import org.logico.util.Constants;

@Tag(name = "Tracking user data REST endpoints")
@Path("/local")
@JBossLog
@AllArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TrackingUserResource {

    private final TrackingUserService trackingUserService;

    //    CRUD
    @POST
    @Path("/create")
    @Operation(summary = "Create mongo user")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TrackingUserDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response createTrackingUser(TrackingUser trackingUser) {
        log.info("Create mongo user");
        trackingUserService.create(trackingUser);
        return Response.ok()
                .status(Status.CREATED)
                .build();
    }

    @GET
    @Path("/users")
    @Operation(summary = "Show mongo users and data")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TrackingUserDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response showTrackingUsers() {
        log.info("Show mongo users");
        return Response.ok()
                .entity(trackingUserService.users())
                .build();
    }

    @PATCH
    @Path("/update/email/{objectId}")
    @Operation(summary = "Update mongo user email by id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TrackingUserDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response updateTrackingUser(@PathParam("objectId") ObjectId objectId, @QueryParam("email") String email) {
        log.info("Update mongo user email by id");
        return Response.ok()
                .entity(trackingUserService.updateEmail(objectId, email))
                .build();
    }

    @PATCH
    @Path("/update/switch/{email}")
    @Operation(summary = "Update mongo user email by id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TrackingUserDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response updateSwitchTracking(@PathParam("email") String email, @QueryParam("switch") boolean swtch) {
        log.info("Update mongo user tracking by id");
        return Response.ok()
                .entity(trackingUserService.updateSwitch(email, swtch))
                .build();
    }

    @DELETE
    @Path("/delete/{email}")
    @Operation(summary = "Delete mongo user by email")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TrackingUserDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response deleteTrackingUser(@PathParam("email") String email) {
        log.info("Delete mongo user");
        return Response.ok()
                .entity(trackingUserService.delete(email))
                .build();
    }

    //    OTHER
    @GET
    @Path("/id/{id}")
    @Operation(summary = "Get mongo user email by mongo id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TrackingUserDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response getTrackingUserEmailById(@PathParam("id") ObjectId id) {
        log.info("Find mongo user email by mongo id");
        return Response.ok()
                .entity(trackingUserService.getEmail(id))
                .build();
    }

    @GET
    @Path("/email/{email}")
    @Operation(summary = "Get mongo user data by email")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TrackingUserDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response findByEmail(@PathParam("email") String email) {
        log.info("Find mongo user by email");
        return Response.ok()
                .entity(trackingUserService.getUser(email))
                .build();
    }
}
