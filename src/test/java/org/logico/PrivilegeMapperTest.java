package org.logico;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.logico.mapper.PrivilegeMapper;
import org.logico.model.Privilege;

import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class PrivilegeMapperTest {
    @Inject
    PrivilegeMapper privilegeMapper;

    @Test
    public void shouldMapPrivilegeToPrivilegeDto() {
        var privilege = new Privilege();
        privilege.setId(1);
        privilege.setName("View Role");

        var privilegeDto = privilegeMapper.privilegeToPrivilegeDto(privilege);

        assertNotNull(privilegeDto);
        assertEquals(privilege.getId(), privilegeDto.getId());
        assertEquals(privilege.getName(), privilegeDto.getName());
    }

    @Test
    public void shouldMapNullPrivilegeToNullDto() {
        var privilegeDto = privilegeMapper.privilegeToPrivilegeDto(null);

        assertNull(privilegeDto);
    }
}
