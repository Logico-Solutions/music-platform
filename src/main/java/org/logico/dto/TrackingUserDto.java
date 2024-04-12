package org.logico.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TrackingUserDto {

    private ObjectId id;
    private String address;
    private String email;
    private PointDto location;
    private boolean switchTracking;
}
