package org.logico;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.logico.dto.PrivilegeDto;
import org.logico.exhandler.PrivilegeNotFoundException;
import org.logico.model.Privilege;
import org.logico.repository.PrivilegeRepository;
import org.logico.service.PrivilegeManagementService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@QuarkusTest
public class PrivilegeManagementServiceTest {

    @InjectMock PrivilegeRepository privilegeRepository;
    @Inject PrivilegeManagementService privilegeManagementService;

    @BeforeEach
    void setUp() {
        Privilege testPrivilege = new Privilege();
        testPrivilege.setId(1);
        testPrivilege.setName("Test privilege 1");
        when(privilegeRepository.findById(1)).thenReturn(testPrivilege);
        when(privilegeRepository.findById(Integer.MAX_VALUE)).thenReturn(null);
    }

    @Test
    void givenPrivilege_whenFindById_thenReturnDto() {

        assertThat(privilegeManagementService.getPrivilegeById(1), notNullValue());
        assertThat(privilegeManagementService.getPrivilegeById(1), isA(PrivilegeDto.class));
        assertThat(privilegeManagementService.getPrivilegeById(1).getId(), equalTo(1));
        assertThat(privilegeManagementService.getPrivilegeById(1).getName(), equalTo("Test privilege 1"));
    }

    @Test
    void noPrivilegeInRepository_whenFindById_thenReturnException() {
        assertThrows(PrivilegeNotFoundException.class,
                () -> {privilegeManagementService.getPrivilegeById(Integer.MAX_VALUE);});
    }
}
