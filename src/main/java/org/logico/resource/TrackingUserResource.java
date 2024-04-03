package org.logico.resource;

import jakarta.inject.Inject;
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
import org.logico.repository.TrackingUserRepository;
import org.logico.util.Constants;

    /*
    Just copy/paste =D
    ---Endpoints---
    Create user example: http://localhost:8080/local/create
    Show users: http://localhost:8080/local/users
    Update user example: http://localhost:8080/local/update/66054ba37a3c8217345c89a2?email=update@ex.com
    Delete user example: http://localhost:8080/local/delete/delete@ex.com

    Enable/Disable tracking: http://localhost:8080/local/update/switch/create@ex.com?switch=true or false

    ---Connection strings---
    Auth: mongodb+srv://user:pass@microservice.nqoux5f.mongodb.net/?retryWrites=true&w=majority&appName=Microservice
    X.509: mongodb+srv://microservice.nqoux5f.mongodb.net/?authSource=%24external&authMechanism=MONGODB-X509&retryWrites=true&w=majority&appName=Microservice

    not @Authenticated
     */

@Tag(name = "Tracking user data REST endpoints")
@Path("/local")
@JBossLog
@AllArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TrackingUserResource {

    @Inject
    TrackingUserRepository trackingUserRepository;

    // CRUD
    @POST
    @Path("/create")
    @Operation(summary = "Create mongo user")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TrackingUserDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response createTrackingUser(TrackingUser trackingUser) {
        try {
            log.info("Create mongo user");
            trackingUserRepository.persist(trackingUser);
            return Response.ok()
                    .status(Status.CREATED)
                    .build();
        } catch (Exception e) {
            log.warnv(e, "Failed to create mongo user");
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/users")
    @Operation(summary = "Show mongo users and data")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TrackingUserDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response showAll() {
        try {
            log.info("Show mongo users");
            return Response.ok()
                    .entity(trackingUserRepository.listAll())
                    .build();
        } catch (Exception e) {
            log.warnv(e, "Failed to show mongo users");
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    //66054ba37a3c8217345c89a2 :test
    //update@ex.com or any :test
    @PATCH
    @Path("/update/email/{objectId}")
    @Operation(summary = "Update mongo user email by id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TrackingUserDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response updateTrackingUser(@PathParam("objectId") ObjectId objectId, @QueryParam("email") String email) {
        try {
            log.info("Update mongo user email by id");
            return Response.ok()
                    .entity(trackingUserRepository.updateEmailById(objectId, email))
                    .build();
        } catch (Exception e) {
            log.warnv(e, "Failed to update mongo user");
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @PATCH
    @Path("/update/switch/{email}")
    @Operation(summary = "Update mongo user email by id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TrackingUserDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response updateSwitchTracking(@PathParam("email") String email, @QueryParam("switch") boolean bool) {
        try {
            log.info("Update mongo user email by id");
            return Response.ok()
                    .entity(trackingUserRepository.updateSwitch(email, bool))
                    .build();
        } catch (Exception e) {
            log.warnv(e, "Failed to update mongo user");
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    // delete@ex.com :test
    @DELETE
    @Path("/delete/{email}")
    @Operation(summary = "Delete mongo user by email")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TrackingUserDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response deleteTrackingUser(@PathParam("email") String email) {
        try {
            log.info("Delete mongo user");
            return Response.ok()
                    .entity(trackingUserRepository.deleteByEmail(email))
                    .build();
        } catch (Exception e) {
            log.warnv(e, "Failed to delete mongo user");
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    // Test database endpoints

    // 65faaf2542dc1d73bbdb7c3c :test
    // 65fc8be71a6b67574bec840d :test
    @GET
    @Path("/id/{objectId}")
    @Operation(summary = "Get mongo user email by mongo id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = Constants.SUCCESSFULLY_RETRIEVED, content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TrackingUserDto.class))}),
            @APIResponse(responseCode = "401", description = "Unauthorized")})
    public Response getUserEmailById(@PathParam("objectId") ObjectId objectId) {
        try {
            log.info("Find mongo user email by mongo id");
            return Response.ok()
                    .entity(trackingUserRepository.getUserEmailById(objectId))
                    .build();
        } catch (Exception e) {
            log.warnv(e, "Failed to find email by mongo id");
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    // example1@ex.com :test
    // example2@ex.com :test
    // ...
    // exampleN@ex.com :test
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
}
