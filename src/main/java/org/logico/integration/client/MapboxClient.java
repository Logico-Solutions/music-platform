package org.logico.integration.client;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "mapbox")
public interface MapboxClient {

    @GET
    @Path("/api/search/geocode/v6/reverse")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response getGeoData(@QueryParam("longitude") double longitude,
            @QueryParam("latitude") double latitude, @QueryParam("access_token") String accessToken);
}
