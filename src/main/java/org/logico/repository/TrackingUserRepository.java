package org.logico.repository;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.logico.model.TrackingUser;

import java.util.Objects;

@ApplicationScoped
public class TrackingUserRepository implements PanacheMongoRepository<TrackingUser> {

    public TrackingUser findUserByEmail(String email) {
        return find("user_email", email).singleResult();
    }

    public UpdateResult updateEmailById(ObjectId id, String email) {
        return mongoCollection().updateOne(new Document("_id", id),
                new Document("$set", new Document("user_email", email)));
    }

    public UpdateResult updateSwitchByEmail(String email, boolean bool) {
        return mongoCollection().updateOne(new Document("user_email", email),
                new Document("$set", new Document("switch_tracking", bool)));
    }

    public UpdateResult updateUserCoordinates(String email, Position position) {
        return mongoCollection().updateOne(new Document("user_email", email),
                new Document("$set", new Document("location", new Point(position))));
    }

    public DeleteResult deleteByEmail(String email) {
        return mongoCollection().deleteOne(Filters.eq("user_email", email));
    }

    public Point getUserCoordinatesByEmail(String email) {
        return Objects.requireNonNull(mongoCollection()
                .find(new Document("user_email", email))
                .first()).getLocation();
    }

    public UpdateResult updateUserAddressByEmail(String email, String address) {
        return mongoCollection().updateOne(new Document("user_email", email),
                new Document("$set", new Document("address", address)));
    }
}
