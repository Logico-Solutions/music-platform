package org.logico.integration;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.jbosslog.JBossLog;
import org.logico.dto.TrackingUserDto;
import org.logico.dto.request.MapboxRequestDto;
import org.logico.dto.response.MapboxResponseDto;
import org.logico.integration.client.MapboxClient;
import org.logico.service.TrackingUserService;

@Builder
@JBossLog
@ApplicationScoped
@AllArgsConstructor
public class MapboxService {

    private final String ACCESS_TOKEN = "pk.eyJ1IjoidGFzdWtlIiwiYSI6ImNsdWxqZHJ2MDB1MHQycWxpbDBwYTh3cm4ifQ.Xq9nfy3UrLgwaR3xNHA0mg";

    private final TrackingUserService trackingUserService;
    private final MapboxClient mapboxClient;

    public MapboxResponseDto getGeoData(String email) {

        TrackingUserDto user = trackingUserService.getUser(email);
        double lat = user.getLocation().getCoordinates().getValues().get(0);
        double lon = user.getLocation().getCoordinates().getValues().get(1);

        MapboxRequestDto request = new MapboxRequestDto(lat, lon);

        return mapboxClient.getGeoData(ACCESS_TOKEN, request);
    }
}
