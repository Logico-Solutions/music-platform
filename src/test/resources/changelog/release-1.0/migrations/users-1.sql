INSERT INTO platform_user
(role_id, full_name, last_name, first_name, username, password, email, status, created_at, created_by, updated_at, updated_by)
VALUES
(1, 'John Doe', 'Doe', 'John', 'johndoe', '2qoEbdvzb/VopQZ4FDLma90ci52A3H2aeNG8ioMWEu8=', 'johndoe@example.com', 'Active', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

INSERT INTO platform_user
(role_id, full_name, last_name, first_name, username, password, email, status, created_at, created_by, updated_at, updated_by)
VALUES
(3, 'John Doe', 'Doe', 'John', 'subadmin', '2qoEbdvzb/VopQZ4FDLma90ci52A3H2aeNG8ioMWEu8=', 'subadmin@example.com', 'Active', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
