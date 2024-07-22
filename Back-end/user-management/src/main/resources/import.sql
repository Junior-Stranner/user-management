-- Inserindo um usuário na tabela tb_user
INSERT INTO tb_user (login, password, email, name) VALUES ('judev', '1234', 'judev@outlook.com', 'Junior');


-- Reiniciando a sequência para a coluna ID da tabela tb_user com o próximo valor sendo 2
--ALTER SEQUENCE tb_user_id_SEQ RESTART WITH 2;

-- Inserindo perfis na tabela tb_profile
INSERT INTO tb_profile (descricao) VALUES ('Administrador');
INSERT INTO tb_profile (descricao) VALUES ('Manager');
INSERT INTO tb_profile (descricao) VALUES ('Client');

-- Reiniciando a sequência para a coluna ID da tabela tb_profile com o próximo valor sendo 4
--ALTER SEQUENCE tb_profile_id_SEQ RESTART WITH 4;

-- Inserindo recursos na tabela tb_resource
INSERT INTO tb_resource (name, resource_key) VALUES ('User screen', 'user');
INSERT INTO tb_resource (name, resource_key) VALUES ('Profile screen', 'profile');
INSERT INTO tb_resource (name, resource_key) VALUES ('Resource screen', 'resource');

-- Reiniciando a sequência para a coluna ID da tabela tb_resource com o próximo valor sendo 4
--ALTER SEQUENCE tb_resource_id_SEQ RESTART WITH 4;
