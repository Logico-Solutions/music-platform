package org.logico.model;

import com.mongodb.client.model.geojson.Point;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity(collection = "tracking_user")
public class TrackingUser extends PanacheMongoEntity {

    @BsonProperty("address")
    String address;

    @BsonProperty("user_email")
    String userEmail;

    @BsonProperty("location")
    Point location;

    @BsonProperty("switch_tracking")
    boolean switchTracking;
}
