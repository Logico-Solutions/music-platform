package org.logico.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.logico.dto.PrivilegeAssignmentDto;

import java.util.List;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleResponseDto {

    @JsonProperty
    private Integer id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String ldapGroup;
    @JsonProperty
    private List<PrivilegeAssignmentDto> privileges;
}
