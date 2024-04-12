package org.logico.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@AllArgsConstructor
@Data
public class MapboxRequestDto {

    @JsonProperty
    private Double lat;
    @JsonProperty
    private Double lon;

}
