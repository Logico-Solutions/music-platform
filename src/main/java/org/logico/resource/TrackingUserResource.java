package org.logico.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
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
import org.logico.repository.TrackingUserRepository;
import org.logico.util.Constants;

@Tag(name = "Tracking user data REST endpoints")
@Path("/local")
@JBossLog
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TrackingUserResource {

    @Inject
    TrackingUserRepository trackingUserRepository;

    @POST
    @Path("/create")
    public Response createTrackingUser(TrackingUser trackingUser){
        try {
            log.info("Create mongo user");
            trackingUserRepository.persist(trackingUser);
            return Response.ok(trackingUser)
                    .status(Status.CREATED)
                    .build();
        } catch (Exception e) {
            log.warnv(e, "Failed to create mongo user");
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    // 65faaf2542dc1d73bbdb7c3c :test
    // 65fc8be71a6b67574bec840d :test
    @GET
    @Path("/id/{objectId}")
    @Operation(summary = "Get mongo user email by id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TrackingUserDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response getUserEmailById(@PathParam("objectId") String objectId) {
        try {
            log.info("Find mongo user email by id");
            ObjectId objId = new ObjectId(objectId);
            return Response.ok()
                    .entity(trackingUserRepository.getUserEmailById(objId))
                    .build();
        } catch (Exception e) {
            log.warnv(e, "Failed to find email by id");
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    // example1@ex.com :test
    // example2@ex.com :test
    @GET
    @Path("/email/{email}")
    @Operation(summary = "Get mongo user data by email")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TrackingUserDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response findByEmail(@PathParam("email") String email) {
        try {
            log.info("Find mongo user by email");
            return Response.ok()
                    .entity(trackingUserRepository.findByEmail(email))
                    .build();
        } catch (Exception e) {
            log.warnv(e, "Failed to find mongo user");
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/all")
    @Operation(summary = "Show all mongo users and data")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TrackingUserDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response showAll() {
        try {
            log.info("Show all mongo users");
            return Response.ok()
                    .entity(trackingUserRepository.findAll().list())
                    .build();
        } catch (Exception e) {
            log.warnv(e, "Failed to show all mongo users");
            return Response.status(Status.BAD_REQUEST).build();
        }
    }
}
