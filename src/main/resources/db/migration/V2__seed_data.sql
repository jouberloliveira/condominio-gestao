-- Roles
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_MORADOR');
INSERT INTO roles (name) VALUES ('ROLE_PORTEIRO');

-- Users (BCrypt of "password123", strength=10)
INSERT INTO users (username, password, email, enabled) VALUES
  ('admin',    '$2b$10$72kx2vf61km1nj8M4rP0T.t5OZ.rUAUf2ax8N5TdkiWUxFI5Shq/m', 'admin@condominio.com',    TRUE),
  ('morador1', '$2b$10$72kx2vf61km1nj8M4rP0T.t5OZ.rUAUf2ax8N5TdkiWUxFI5Shq/m', 'morador1@condominio.com', TRUE),
  ('porteiro1','$2b$10$72kx2vf61km1nj8M4rP0T.t5OZ.rUAUf2ax8N5TdkiWUxFI5Shq/m', 'porteiro1@condominio.com',TRUE);

-- Assign roles
INSERT INTO user_roles (user_id, role_id)
  SELECT u.id, r.id FROM users u, roles r WHERE u.username = 'admin'     AND r.name = 'ROLE_ADMIN';
INSERT INTO user_roles (user_id, role_id)
  SELECT u.id, r.id FROM users u, roles r WHERE u.username = 'morador1'  AND r.name = 'ROLE_MORADOR';
INSERT INTO user_roles (user_id, role_id)
  SELECT u.id, r.id FROM users u, roles r WHERE u.username = 'porteiro1' AND r.name = 'ROLE_PORTEIRO';

-- Sample unit
INSERT INTO unidades (bloco, numero, andar, situacao, identificacao)
  VALUES ('A', '101', '1', 'OCUPADA', '101-A');

-- Sample morador linked to morador1
INSERT INTO moradores (unidade_id, nome, cpf, email, tipo_morador, responsavel_unidade)
  SELECT id, 'Morador Um', '123.456.789-09', 'morador1@condominio.com', 'PROPRIETARIO', 'SIM'
  FROM unidades WHERE identificacao = '101-A';
