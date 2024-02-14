package org.logico;


import org.junit.jupiter.api.Test;
import org.logico.dto.PrivilegeDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PrivilegeDtoTest {

    @Test
    public void shouldCreatePrivilegeDtoUsingNoArgsConstructor() {
        var privilege = new PrivilegeDto();
        privilege.setId(1);
        privilege.setName("View Role");

        assertThat(privilege.getId(), is(1));
        assertThat(privilege.getName(), is("View Role"));
    }

    @Test
    public void shouldCreatePrivilegeDtoUsingAllArgsConstructor() {
        var privilege = new PrivilegeDto(1, "View Role");

        assertThat(privilege.getId(), is(1));
        assertThat(privilege.getName(), is("View Role"));
    }

    @Test
    public void shouldCreatePrivilegeDtoUsingBuilder() {
        var privilege = PrivilegeDto.builder()
                .id(1)
                .name("View Role")
                .build();

        assertThat(privilege.getId(), is(1));
        assertThat(privilege.getName(), is("View Role"));
    }
}
