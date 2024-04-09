package org.logico.service;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.bson.types.ObjectId;
import org.logico.model.TrackingUser;
import org.logico.repository.TrackingUserRepository;

import java.util.List;
import java.util.Objects;

@JBossLog
@RequiredArgsConstructor
@ApplicationScoped
public class TrackingUserService {

    private final JwtClaimService jwtClaimService;
    private final TrackingUserRepository trackingUserRepository;

    public void create(TrackingUser trackingUser) {
        trackingUserRepository.persistOrUpdate(trackingUser);
    }

    public List<TrackingUser> users() {
        log.debug("Showing as list tracking_user's from MongoDB!");
        return trackingUserRepository.listAll();
    }

    public boolean delete(String email) {
        log.debug("Deleting tracking_user from MongoDB by email!");
        long result = trackingUserRepository.deleteByEmail(email).getDeletedCount();
        return result > 0;
    }

    public String updateEmail(ObjectId id, String email) {
        log.debug("Updating tracking_user email from MongoDB by objectId!");
        return Objects.requireNonNull(trackingUserRepository.updateEmailById(id, email)).toString();
    }

    public String updateSwitch(String email, boolean swtch) {
        log.debug("Updating tracking_user switch_tracking from MongoDB by email!");
        return Objects.requireNonNull(trackingUserRepository.updateSwitchByEmail(email, swtch)).toString();
    }

    public TrackingUser getUser(String email) {
        log.debug("Getting tracking_user from MongoDB by email!");
        return trackingUserRepository.find("user_email", email).singleResult();
    }

    public String getEmail(ObjectId id) {
        log.debug("Getting tracking_user email from MongoDB by objectId!");
        return trackingUserRepository.findById(id).getUserEmail();
    }
}
