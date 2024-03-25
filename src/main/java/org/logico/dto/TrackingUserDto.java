package org.logico.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.client.model.geojson.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TrackingUserDto {

    @JsonProperty
    private String address;
    @JsonProperty
    private String email;
    @JsonProperty
    private Point coordinates;
    @JsonProperty
    private boolean switchTracking;
}
