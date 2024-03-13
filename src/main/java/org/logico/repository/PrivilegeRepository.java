package org.logico.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;
import jakarta.enterprise.context.ApplicationScoped;
import org.logico.model.Privilege;

import java.util.List;

@ApplicationScoped
public class PrivilegeRepository implements PanacheRepositoryBase<Privilege, Long> {

    public List<Privilege> findPaginatedAndSortedById(int pageNumber, int pageSize, Direction direction) {
        return findAll(Sort.by("id").direction(direction))
                .page(Page.of(pageNumber, pageSize))
                .list();
    }
}
