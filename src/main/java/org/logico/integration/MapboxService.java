package org.logico.integration;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.jbosslog.JBossLog;
import org.logico.dto.TrackingUserDto;
import org.logico.dto.response.MapboxResponseDto;
import org.logico.integration.client.MapboxClient;
import org.logico.mapper.MapboxMapper;
import org.logico.service.TrackingUserService;

@Builder
@JBossLog
@ApplicationScoped
@AllArgsConstructor
public class MapboxService {

    private final String ACCESS_TOKEN = "pk.eyJ1IjoidGFzdWtlIiwiYSI6ImNsdWxqZHJ2MDB1MHQycWxpbDBwYTh3cm4ifQ.Xq9nfy3UrLgwaR3xNHA0mg";

    private final TrackingUserService trackingUserService;
    private final MapboxClient mapboxClient;
    private final MapboxMapper mapboxMapper;

    public TrackingUserDto getGeoData(String email, double lon, double lat) {
        trackingUserService.updatePosition(email, lon, lat);

        MapboxResponseDto response = mapboxClient.getGeoData(ACCESS_TOKEN, lon, lat);
        String address = response.getFeatures().get(0).getProperties().getFull_address();

        return trackingUserService.updateAddress(email, address);
    }

}
