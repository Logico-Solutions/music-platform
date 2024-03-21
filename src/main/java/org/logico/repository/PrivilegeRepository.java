package org.logico.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.logico.model.Privilege;

@ApplicationScoped
public class PrivilegeRepository implements PanacheRepositoryBase<Privilege, Integer> {

}
