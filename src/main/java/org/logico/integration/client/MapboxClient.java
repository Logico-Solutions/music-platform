package org.logico.integration.client;

import jakarta.enterprise.inject.Default;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.logico.dto.response.MapboxResponseDto;

@Default
@RegisterRestClient(configKey = "mapbox-api")
public interface MapboxClient {

    @GET
    @Path("/search/geocode/v6/reverse")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    MapboxResponseDto getGeoData(@QueryParam("access_token") String accessToken, @QueryParam("longitude") Double lon, @QueryParam("latitude") Double lat);
}
