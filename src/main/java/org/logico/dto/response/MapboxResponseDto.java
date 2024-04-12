package org.logico.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MapboxResponseDto {

    @JsonProperty
    private String type;
    @JsonProperty
    private List<Feature> features;
    @JsonProperty
    private String attribution;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Feature {
        private String type;
        private String id;
        private Geometry geometry;
        private Properties properties;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Geometry {
        private String type;
        private List<Double> coordinates;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Properties {

        @JsonProperty
        private String mapbox_id;
        @JsonProperty
        private String feature_type;
        @JsonProperty
        private String full_address;
        @JsonProperty
        private String name;
    }
}
