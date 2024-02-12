package org.logico.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampJdbcType;
import org.logico.audit.AuditListener;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder(toBuilder = true)
@MappedSuperclass
@EntityListeners(AuditListener.class)
public abstract class AuditEntity {

    @Column(name = "created_at", updatable = false)
    @JdbcType(TimestampJdbcType.class)
    private Instant createdAt;

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "updated_at")
    @JdbcType(TimestampJdbcType.class)
    private Instant updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;
}
