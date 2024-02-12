package org.logico.util;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class PrivilegeName {

    public static final String VIEW_ROLE = "View Role";
    public static final String CREATE_ROLE = "Create Role";
    public static final String UPDATE_ROLE = "Update Role";
    public static final String DELETE_ROLE = "Delete Role";

    public static final List<String> ALL_PRIVILEGES = List.of(
            VIEW_ROLE,
            CREATE_ROLE,
            UPDATE_ROLE,
            DELETE_ROLE
    );
}
