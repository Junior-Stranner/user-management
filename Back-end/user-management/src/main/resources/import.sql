-- Inserindo perfis na tabela tb_profile
INSERT INTO tb_profile (descricao) VALUES ('Administrador');
INSERT INTO tb_profile (descricao) VALUES ('Manager');
INSERT INTO tb_profile (descricao) VALUES ('Client');

--ALTER SEQUENCE tb_profile_id_seq RESTART WITH 4;

-- Inserindo um usuário na tabela tb_user
INSERT INTO tb_user (login, password, email, name) VALUES ('judev', '1234', 'judev@outlook.com', 'Junior');
INSERT INTO tb_user (login, password, email, name) VALUES ('Bia', '98989', 'bia@outlook.com', 'Beatriz');
INSERT INTO tb_user (login, password, email, name) VALUES ('Lucas', '2312', 'lusca00@outlook.com', 'Lucas');

--ALTER SEQUENCE tb_user_id_seq RESTART WITH 4;

-- Inserindo dados na tabela tb_profile_user após a inserção dos perfis e usuários
INSERT INTO tb_profile_user (profile_id, user_id) VALUES (1, 1);
INSERT INTO tb_profile_user (profile_id, user_id) VALUES (2, 2);
INSERT INTO tb_profile_user (profile_id, user_id) VALUES (3, 2);
INSERT INTO tb_profile_user (profile_id, user_id) VALUES (3, 3);

--ALTER SEQUENCE tb_profile_user_id_seq RESTART WITH 4;

-- Inserindo recursos na tabela tb_resource
INSERT INTO tb_resource (name, resource_key) VALUES ('User screen', 'user');
INSERT INTO tb_resource (name, resource_key) VALUES ('Profile screen', 'profile');
INSERT INTO tb_resource (name, resource_key) VALUES ('Resource screen', 'resource');

--ALTER SEQUENCE tb_resource_id_seq RESTART WITH 4;

