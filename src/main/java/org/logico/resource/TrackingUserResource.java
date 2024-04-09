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
@Path("/location")
@JBossLog
@AllArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TrackingUserResource {

    private final TrackingUserService trackingUserService;

    @POST
    @Path("/create-user")
    @Operation(summary = "Create mongo user")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TrackingUserDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response createTrackingUser(TrackingUser trackingUser) {
        log.info("Create mongo user");
        return Response.ok()
                .entity(trackingUserService.create(trackingUser))
                .build();
    }

    @GET
    @Path("/show-users")
    @Operation(summary = "Show mongo users and data")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON)}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response showTrackingUsers() {
        log.info("Show mongo users");
        return Response.ok()
                .entity(trackingUserService.users())
                .build();
    }

    @GET
    @Path("/user-get-by-id/{id}")
    @Operation(summary = "Get mongo user email by mongo id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON)}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response getTrackingUserEmail(@PathParam("id") ObjectId id) {
        log.info("Find mongo user email by mongo id");
        return Response.ok()
                .entity(trackingUserService.getEmail(id))
                .build();
    }

    @PATCH
    @Path("/email-update-by-id/{id}")
    @Operation(summary = "Update mongo user email by id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON)}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response updateTrackingUser(@PathParam("id") ObjectId id, @QueryParam("email") String email) {
        log.info("Update mongo user email by id");
        return Response.ok()
                .entity(trackingUserService.updateEmail(id, email))
                .build();
    }

    @PATCH
    @Path("/switch-update-by-email/{email}")
    @Operation(summary = "Update mongo user email by id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON)}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response updateSwitchTracking(@PathParam("email") String email, @QueryParam("switch") boolean swtch) {
        log.info("Update mongo user tracking by id");
        return Response.ok()
                .entity(trackingUserService.updateSwitch(email, swtch))
                .build();
    }

    @DELETE
    @Path("/user-delete-by-email/{email}")
    @Operation(summary = "Delete mongo user by email")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON)}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response deleteTrackingUser(@PathParam("email") String email) {
        log.info("Delete mongo user");
        return Response.ok()
                .entity(trackingUserService.delete(email))
                .build();
    }

    @GET
    @Path("/user-get-by-email/{email}")
    @Operation(summary = "Get mongo user data by email")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON)}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response getTrackingUser(@PathParam("email") String email) {
        log.info("Find mongo user by email");
        return Response.ok()
                .entity(trackingUserService.getUser(email))
                .build();
    }

    @GET
    @Path("/position-get-by-email/{email}")
    @Operation(summary = "Get mongo user position by email")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON)}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response getTrackingUserPosition(@PathParam("email") String email) {
        log.info("Find position mongo user by email");
        return Response.ok()
                .entity(trackingUserService.getPosition(email))
                .build();
    }

    @PATCH
    @Path("/position-update-by-email/{email}")
    @Operation(summary = "Update mongo user position by email")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON)}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response updateTrackingUserPosition(@PathParam("email") String email,
            @QueryParam("longitude") double lon, @QueryParam("latitude") double lat) {
        log.info("Update position mongo user by email");
        return Response.ok()
                .entity(trackingUserService.updatePosition(email, lon, lat))
                .build();
    }
}
