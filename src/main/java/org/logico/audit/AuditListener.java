package org.logico.audit;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.logico.model.AuditEntity;

import java.time.Instant;

@ApplicationScoped
@AllArgsConstructor
public class AuditListener {

    private final JsonWebToken jsonWebToken;

    @PrePersist
    @PreUpdate
    public void setAuditFields(AuditEntity entity) {
        entity.setUpdatedBy(jsonWebToken.getName());
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(Instant.now());
            entity.setCreatedBy(jsonWebToken.getName());
        }
        entity.setUpdatedAt(Instant.now());
    }
}
