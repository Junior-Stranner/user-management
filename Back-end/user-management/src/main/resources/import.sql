-- Inserindo perfis na tabela tb_profile
INSERT INTO tb_profile (id, descricao) VALUES (1, 'Administrador');
INSERT INTO tb_profile (id, descricao) VALUES (2, 'Manager');
INSERT INTO tb_profile (id, descricao) VALUES (3, 'Client');

-- Inserindo um usuário na tabela tb_user
INSERT INTO tb_user (id, login, password, email, name) VALUES (1, 'judev', '1234', 'judev@outlook.com', 'Junior');
INSERT INTO tb_user (id, login, password, email, name) VALUES (2, 'Bia', '98989', 'bia@outlook.com', 'Beatriz');
INSERT INTO tb_user (id, login, password, email, name) VALUES (3, 'Lucas', '2312', 'lusca00@outlook.com', 'Lucas');

-- Inserindo dados na tabela tb_profile_user após a inserção dos perfis e usuários
INSERT INTO tb_profile_user (profile_id, user_id) VALUES (1, 1);
INSERT INTO tb_profile_user (profile_id, user_id) VALUES (2, 2);
INSERT INTO tb_profile_user (profile_id, user_id) VALUES (3, 2);
INSERT INTO tb_profile_user (profile_id, user_id) VALUES (3, 3);

-- Inserindo recursos na tabela tb_resource
INSERT INTO tb_resource (name, resource_key) VALUES ('User screen', 'user');
INSERT INTO tb_resource (name, resource_key) VALUES ('Profile screen', 'profile');
INSERT INTO tb_resource (name, resource_key) VALUES ('Resource screen', 'resource');
