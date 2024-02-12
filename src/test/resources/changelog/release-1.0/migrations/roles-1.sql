-- System Roles Initial Config (just to allow test users to login, privileges can be updated through UI)
INSERT INTO role (name, role_id, created_at, created_by, updated_at, updated_by)
VALUES ('Admin-1', 1, CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system');

INSERT INTO role (name, role_id, created_at, created_by, updated_at, updated_by)
VALUES ('Sub-admin-1', 2, CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system');

INSERT INTO role (name, role_id, created_at, created_by, updated_at, updated_by)
VALUES ('Sub-admin-2', 3, CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system');

-- Mapping Roles to Privileges
INSERT INTO privilege_assignment (role_id, privilege_id, type, assigned_at)
VALUES (1, 1, 'Allowed', CURRENT_TIMESTAMP);
INSERT INTO privilege_assignment (role_id, privilege_id, type, assigned_at)
VALUES (1, 2, 'Allowed', CURRENT_TIMESTAMP);
INSERT INTO privilege_assignment (role_id, privilege_id, type, assigned_at)
VALUES (1, 3, 'Allowed', CURRENT_TIMESTAMP);
INSERT INTO privilege_assignment (role_id, privilege_id, type, assigned_at)
VALUES (1, 4, 'Allowed', CURRENT_TIMESTAMP);

INSERT INTO privilege_assignment (role_id, privilege_id, type, assigned_at)
VALUES (2, 1, 'Allowed', CURRENT_TIMESTAMP);
INSERT INTO privilege_assignment (role_id, privilege_id, type, assigned_at)
VALUES (2, 2, 'Allowed', CURRENT_TIMESTAMP);


