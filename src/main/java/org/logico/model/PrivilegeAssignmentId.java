package org.logico.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class PrivilegeAssignmentId implements Serializable {

    private static final long serialVersionUID = 3212171240878842905L;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "privilege_id")
    private Integer privilegeId;
}
