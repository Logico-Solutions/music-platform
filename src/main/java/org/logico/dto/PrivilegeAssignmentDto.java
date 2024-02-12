package org.logico.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.logico.model.PrivilegeAssignmentType;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PrivilegeAssignmentDto extends PrivilegeDto {

    @JsonProperty
    @NotNull(message = "Privilege type can not be null")
    private PrivilegeAssignmentType type;
}
