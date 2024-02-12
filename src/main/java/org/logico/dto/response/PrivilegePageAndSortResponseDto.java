package org.logico.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.logico.dto.PrivilegeDto;

import java.util.List;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrivilegePageAndSortResponseDto {

    @JsonProperty
    private List<PrivilegeDto> privilegeDtoList;
}
