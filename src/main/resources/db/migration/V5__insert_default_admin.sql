-- Insert default admin user
-- Username: admin
-- Password: admin123 (hashed using BCrypt)
INSERT INTO users (username, email, password, role, enabled)
VALUES (
    'admin',
    'admin@eclair.app',
    '$2a$10$14k0yeUIijV5poLk4i38Luawk0Ul/VqmxD.e5QmRccNaqbndaBdJu',
    'ADMIN',
    true
);
