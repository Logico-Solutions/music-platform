package org.logico.model;


import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampJdbcType;
import org.logico.model.converter.PrivilegeAssignmentTypeConverter;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "privilege_assignment")
public class PrivilegeAssignment {

    @EmbeddedId
    private PrivilegeAssignmentId id = new PrivilegeAssignmentId();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @MapsId("roleId")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "privilege_id")
    @MapsId("privilegeId")
    private Privilege privilege;

    @Column(name = "type", nullable = false)
    @Convert(converter = PrivilegeAssignmentTypeConverter.class)
    private PrivilegeAssignmentType type;

    @Column(name = "assigned_at", nullable = false)
    @JdbcType(TimestampJdbcType.class)
    private Instant assignedAt;

    public PrivilegeAssignment(Role role, Privilege privilege, PrivilegeAssignmentType type, Instant assignedAt) {
        this.role = role;
        this.privilege = privilege;
        this.type = type;
        this.assignedAt = assignedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrivilegeAssignment other)) {
            return false;
        }
        if (id == null || (id.getRoleId() == null && id.getPrivilegeId() == null)) {
            return super.equals(o);
        }
        return id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
