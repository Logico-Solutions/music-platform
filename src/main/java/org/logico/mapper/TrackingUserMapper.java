package org.logico.mapper;

import com.mongodb.client.model.geojson.Point;
import org.logico.dto.PointDto;
import org.logico.dto.TrackingUserDto;
import org.logico.model.TrackingUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "jakarta")
public interface TrackingUserMapper {

    @Mapping(source = "location.coordinates", target = "location")
    TrackingUserDto toDto(TrackingUser trackingUser);

    @Mapping(source = "location.coordinates", target = "location")
    List<TrackingUserDto> toListDto(List<TrackingUser> trackingUser);

    @Mapping(source = "position", target = "position")
    PointDto toPointDto(Point point);
}
