package org.logico;

import org.junit.jupiter.api.Test;
import org.logico.dto.PrivilegeDto;
import org.logico.dto.response.PrivilegePageAndSortResponseDto;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PrivilegePageAndSortResponseDtoTest {

    @Test
    public void shouldHandlePrivilegeDtoList() {
        var privilege1 = new PrivilegeDto(1, "View Role");
        var privilege2 = new PrivilegeDto(2, "Edit User");

        var privilegeList = Arrays.asList(privilege1, privilege2);
        var response = new PrivilegePageAndSortResponseDto(privilegeList);

        assertThat(response.getPrivilegeDtoList(), hasSize(2));
        assertThat(response.getPrivilegeDtoList(), contains(privilege1, privilege2));
    }

    @Test
    public void shouldCreateEmptyPrivilegePageAndSortResponseDto() {
        var response = new PrivilegePageAndSortResponseDto();

        assertThat(response.getPrivilegeDtoList(), is(nullValue()));
    }

    @Test
    public void shouldSetPrivilegeDtoList() {
        var privilege = new PrivilegeDto(1, "View Role");
        var response = new PrivilegePageAndSortResponseDto();
        response.setPrivilegeDtoList(List.of(privilege));

        assertThat(response.getPrivilegeDtoList(), hasSize(1));
        assertThat(response.getPrivilegeDtoList(), hasItem(privilege));
    }
}
