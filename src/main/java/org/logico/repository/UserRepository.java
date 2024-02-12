package org.logico.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import org.logico.model.User;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, Integer> {

    public Optional<User> findUserByUsername(String username) {
        return find("username = (:username)", Parameters.with("username", username)).firstResultOptional();
    }
}
