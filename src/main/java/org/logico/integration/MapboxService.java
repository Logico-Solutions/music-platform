package org.logico.integration;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.logico.integration.client.MapboxClient;
import org.logico.service.TrackingUserService;

@JBossLog
@ApplicationScoped
public class MapboxService implements MapboxClient {

    MapboxClient mapboxClient;
    TrackingUserService trackingUserService;

    @Retry(maxRetries = 1, abortOn = {NotFoundException.class})
    public Response getGeoData(double longitude, double latitude, String accessToken) {
        log.info("Trying to get geo data from mapbox api");
        return null;
    }
}
