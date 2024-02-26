package org.logico.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.logico.model.Role;


@ApplicationScoped
public class RoleRepository implements PanacheRepositoryBase<Role, Integer> {


}
