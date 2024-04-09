package org.logico.service;

import com.mongodb.MongoException;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import com.mongodb.client.result.UpdateResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.bson.types.ObjectId;
import org.logico.dto.PointDto;
import org.logico.dto.TrackingUserDto;
import org.logico.mapper.TrackingUserMapper;
import org.logico.model.TrackingUser;
import org.logico.repository.TrackingUserRepository;

import java.util.List;

@JBossLog
@RequiredArgsConstructor
@ApplicationScoped
public class TrackingUserService {

    private final TrackingUserRepository trackingUserRepository;
    private final TrackingUserMapper trackingUserMapper;

    public TrackingUserDto create(TrackingUser trackingUser) {
        trackingUserRepository.persistOrUpdate(trackingUser);
        return trackingUserMapper.toDto(trackingUser);
    }

    public List<TrackingUserDto> users() {
        log.debug("Showing as list tracking_user's from mongodb");
        List<TrackingUser> trackingUsersFromDB = trackingUserRepository.listAll();
        return trackingUserMapper.toListDto(trackingUsersFromDB);
    }

    public boolean delete(String email) {
        log.debug("Deleting tracking_user from mongodb by email");
        long result = trackingUserRepository.deleteByEmail(email).getDeletedCount();
        if (result == 0) {
            throw new MongoException("User NOT FOUND");
        }
        return result > 0;
    }

    public TrackingUserDto updateEmail(ObjectId id, String email) {
        log.debug("Updating tracking_user email from mongodb by objectId");
        UpdateResult upRes = trackingUserRepository.updateEmailById(id, email);
        if (upRes.getMatchedCount() == 0) {
            throw new MongoException("User with id " + id + " NOT FOUND");
        }
        TrackingUser up = trackingUserRepository.findById(id);
        return trackingUserMapper.toDto(up);
    }

    public TrackingUserDto updateSwitch(String email, boolean swtch) {
        log.debug("Updating tracking_user switch_tracking from mongodb by email");
        UpdateResult upRes = trackingUserRepository.updateSwitchByEmail(email, swtch);
        if (upRes.getMatchedCount() == 0) {
            throw new MongoException("User with email " + email + " NOT FOUND");
        }
        TrackingUser up = trackingUserRepository.findUserByEmail(email);
        return trackingUserMapper.toDto(up);
    }

    public TrackingUserDto getUser(String email) {
        log.debug("Getting tracking_user from mongodb by email");
        TrackingUser trackingUser = trackingUserRepository.findUserByEmail(email);
        return trackingUserMapper.toDto(trackingUser);
    }

    public String getEmail(ObjectId id) {
        log.debug("Getting tracking_user email from mongodb by objectId");
        return trackingUserRepository.findById(id).getEmail();
    }

    public PointDto getPosition(String email) {
        log.debug("Getting tracking_user position from mongodb by email");
        Point point = trackingUserRepository.getUserCoordinatesByEmail(email);
        if (point == null)
            throw new MongoException("User position by " + email + " NOT FOUND");
        return trackingUserMapper.toPointDto(point);
    }

    public TrackingUser updatePosition(String email, double lon, double lat) {
        log.debug("Updating tracking_user position from mongodb by email");
        TrackingUser up = trackingUserRepository.findUserByEmail(email);
        if (up == null)
            throw new MongoException("User with email " + email + " NOT FOUND");
        trackingUserRepository.updateUserCoordinates(email, new Position(lon, lat));
        return up;
    }
}
