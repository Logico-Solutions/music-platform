package org.logico;


import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.logico.repository.PrivilegeRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@QuarkusTest
public class PrivilegeRepositoryTest {

    @Inject
    PrivilegeRepository privilegeRepository;

    @Test
    void givenPrivilegeInRepository_whenFindById_shouldReturnPrivilege() {
        assertThat(privilegeRepository.findById(1), notNullValue());
        assertThat(privilegeRepository.findById(1).getId(), equalTo(1));
        assertThat(privilegeRepository.findById(1).getName(), equalTo("View Role"));

    }

    @Test
    void noPrivilegeInRepository_whenFindById_shouldReturnNull() {
        assertThat(privilegeRepository.findById(Integer.MAX_VALUE), nullValue());

    }
}
