package org.logico.repository;

import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.logico.model.TrackingUser;

@ApplicationScoped
public class TrackingUserRepository implements PanacheMongoRepository<TrackingUser> {

    public DeleteResult deleteByEmail(String email) {
        return mongoCollection().deleteOne(Filters.eq("user_email", email));
    }

    public UpdateResult updateEmailById(ObjectId id, String email) {
        return mongoCollection().updateOne(new Document("_id", id),
                new Document("$set", new Document("user_email", email)));
    }

    // Features methods

    public UpdateResult updateSwitchByEmail(String email, boolean bool) {
        return mongoCollection().updateOne(new Document("user_email", email),
                new Document("$set", new Document("switch_tracking", bool)));
    }

    public UpdateResult updateUserCoordinates(double[] coordinates) {return null;}
}
