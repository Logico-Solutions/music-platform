package org.logico.resource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.logico.model.Role;
import org.logico.service.RoleManagementService;

public class RoleManagementResourceTest {
    @Test
    public void testGetRoleById_found() {

        // Створення фіктивного RoleManagementService за допомогою Mockito
        RoleManagementService mockService = Mockito.mock(RoleManagementService.class);
        Role expectedRole = createTestRole();
        when(mockService.findById(1)).thenReturn(expectedRole);

        // Створення екземпляру ресурсу з використанням фіктивного сервісу
        RoleManagementResource resource = new RoleManagementResource(mockService);

        // Виклик тестованого методу
        Role actualRole = resource.getRoleById(1);

        // Перевірка очікуваної відповіді
        assertEquals(expectedRole, actualRole);
    }

    // Додаткові тестові випадки для інших сценаріїв (не знайдено, винятки тощо)

    private Role createTestRole() {
        // Створення тестового об'єкта Role для використання в твердженнях
        Role role = new Role();
        role.setId(1);
        role.setName("Test role");
        return role;
    }
}

