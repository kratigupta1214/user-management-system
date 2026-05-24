-- =============================================================================
-- Sample dummy data for Spring Boot User Management API (portfolio / demo)
-- Loaded on startup when spring.sql.init.mode=always
-- =============================================================================

INSERT INTO users (first_name, last_name, email, phone, date_of_birth, active, created_at, updated_at)
SELECT 'Priya', 'Sharma', 'priya.sharma@example.com', '+919876543210', '1992-03-15', true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'priya.sharma@example.com');

INSERT INTO users (first_name, last_name, email, phone, date_of_birth, active, created_at, updated_at)
SELECT 'Rahul', 'Verma', 'rahul.verma@example.com', '+919812345678', '1988-07-22', true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'rahul.verma@example.com');

INSERT INTO users (first_name, last_name, email, phone, date_of_birth, active, created_at, updated_at)
SELECT 'Ananya', 'Patel', 'ananya.patel@example.com', '9876501234', '1995-11-08', true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'ananya.patel@example.com');

INSERT INTO users (first_name, last_name, email, phone, date_of_birth, active, created_at, updated_at)
SELECT 'James', 'Wilson', 'james.wilson@example.com', '+14155552671', '1990-01-30', true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'james.wilson@example.com');

INSERT INTO users (first_name, last_name, email, phone, date_of_birth, active, created_at, updated_at)
SELECT 'Sarah', 'Johnson', 'sarah.johnson@example.com', '+442071234567', '1993-09-12', false, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'sarah.johnson@example.com');

INSERT INTO users (first_name, last_name, email, phone, date_of_birth, active, created_at, updated_at)
SELECT 'Michael', 'Brown', 'michael.brown@example.com', '5551234567', '1985-12-05', true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'michael.brown@example.com');

INSERT INTO users (first_name, last_name, email, phone, date_of_birth, active, created_at, updated_at)
SELECT 'Emily', 'Davis', 'emily.davis@example.com', '+61398765432', '1997-04-18', true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'emily.davis@example.com');

INSERT INTO users (first_name, last_name, email, phone, date_of_birth, active, created_at, updated_at)
SELECT 'Arjun', 'Mehta', 'arjun.mehta@example.com', '+919988776655', '1991-06-25', true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'arjun.mehta@example.com');

INSERT INTO users (first_name, last_name, email, phone, date_of_birth, active, created_at, updated_at)
SELECT 'Lisa', 'Anderson', 'lisa.anderson@example.com', '2125550198', '1989-08-03', true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'lisa.anderson@example.com');

INSERT INTO users (first_name, last_name, email, phone, date_of_birth, active, created_at, updated_at)
SELECT 'David', 'Miller', 'david.miller@example.com', '+4915123456789', '1994-02-14', false, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'david.miller@example.com');
