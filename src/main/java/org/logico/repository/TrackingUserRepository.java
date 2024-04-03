package org.logico.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;
import org.logico.model.TrackingUser;

@ApplicationScoped
public class TrackingUserRepository implements PanacheMongoRepository<TrackingUser> {

    public TrackingUser findByEmail(String email) {
        return find("user_email", email).singleResult();
    }

    public String getUserEmailById(ObjectId objectId) {
        return findById(objectId).getUserEmail();
    }
}
