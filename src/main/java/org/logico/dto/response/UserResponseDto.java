package org.logico.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseDto {

    @JsonProperty
    private Integer id;
    @JsonProperty
    private String fullName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String username;
    @JsonProperty
    private String email;
    @JsonProperty
    private String status;
    @JsonProperty
    private String role;
}
