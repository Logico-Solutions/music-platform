package org.logico.repository;

import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.logico.model.TrackingUser;

//Create operation use in resource.TrackingUser @POST @Path("/create")

//Read operation in resource.TrackingUser @GET @Path("/users")

@ApplicationScoped
public class TrackingUserRepository implements PanacheMongoRepository<TrackingUser> {

    public DeleteResult deleteByEmail(String email) {
        return mongoCollection().deleteOne(Filters.eq("user_email", email));
    }

    public UpdateResult updateEmailById(ObjectId objectId, String email) {
        return mongoCollection().updateOne(new Document("_id", objectId),
                new Document("$set", new Document("user_email", email)));
    }

    // Features methods

    public UpdateResult updateSwitch(String email, boolean bool) {
        return mongoCollection().updateOne(new Document("user_email", email),
                new Document("$set", new Document("switch_tracking", bool)));
    }

    public String updateUserCoordinates(double[] coordinates) {return null;}

    // Other methods
    public String getUserEmailById(ObjectId objectId) {
        return findById(objectId).getUserEmail();
    }

    public TrackingUser findByEmail(String email) {
        return find("user_email", email).singleResult();
    }
}
