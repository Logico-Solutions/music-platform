package org.logico.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PrivilegeDto {

    @JsonProperty
    @NotNull(message = "Privilege id can not be null")
    private Integer id;
    @JsonProperty
    @NotBlank(message = "Privilege name can not be empty")
    @Schema(example = "View Role")
    private String name;
}
