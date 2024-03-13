package org.logico.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<User> users = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PrivilegeAssignment> privilegeAssignments = new HashSet<>();

    public void addPrivilege(@NonNull Privilege privilege, PrivilegeAssignmentType type) {
        Predicate<Privilege> privilegeContains = pa -> privilegeAssignments.stream()
                .map(PrivilegeAssignment::getPrivilege)
                .anyMatch(p -> p.equals(privilege));

        if (!privilegeContains.test(privilege)) {
            var privilegeAssigment = new PrivilegeAssignment(this, privilege, type, Instant.now());
            privilegeAssignments.add(privilegeAssigment);
            privilege.getPrivilegeAssignments().add(privilegeAssigment);
        }
    }

    public void addOrUpdatePrivilege(@NonNull Privilege privilege, PrivilegeAssignmentType type) {
        Predicate<Privilege> privilegeContains = pa -> privilegeAssignments.stream()
                .map(PrivilegeAssignment::getPrivilege)
                .anyMatch(p -> p.equals(privilege));
        if (!privilegeContains.test(privilege)) {
            var privilegeAssigment = new PrivilegeAssignment(this, privilege, type, Instant.now());
            privilegeAssignments.add(privilegeAssigment);
            privilege.getPrivilegeAssignments().add(privilegeAssigment);
        } else {
            var assignmentNeedUpdate = privilegeAssignments.stream()
                    .filter(privilegeAssignment ->
                            privilegeAssignment.getPrivilege().equals(privilege) &&
                                    privilegeAssignment.getType() != type)
                    .findFirst();
            assignmentNeedUpdate.ifPresent(privilegeAssignment -> {
                privilegeAssignment.setType(type);
                privilegeAssignments.add(privilegeAssignment);
                privilege.getPrivilegeAssignments().add(privilegeAssignment);
            });
        }
    }
}
