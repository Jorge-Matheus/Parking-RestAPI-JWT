insert into USUARIOS (id, username, password, role) values (100, 'ana@email.com', '$2a$12$aDxVS1vfLc4k9I0fVrjYhuCtEvUrXbacnWio9KovPv4bCikDC7V8G', 'ROLE_ADMIN');

insert into USUARIOS (id, username, password, role) values (101, 'bia@email.com', '$2a$12$aDxVS1vfLc4k9I0fVrjYhuCtEvUrXbacnWio9KovPv4bCikDC7V8G', 'ROLE_CLIENTE');

insert into USUARIOS (id, username, password, role) values (102, 'bob@email.com', '$2a$12$aDxVS1vfLc4k9I0fVrjYhuCtEvUrXbacnWio9KovPv4bCikDC7V8G', 'ROLE_CLIENTE');


insert into USUARIOS (id, username, password, role) values (103, 'toby@email.com', '$2a$12$aDxVS1vfLc4k9I0fVrjYhuCtEvUrXbacnWio9KovPv4bCikDC7V8G', 'ROLE_CLIENTE');


insert into CLIENTES (id, nome, cpf, id_usuario) values (10, 'Bianca Silva', '65434217039', 101);
insert into CLIENTES (id, nome, cpf, id_usuario) values (20, 'Roberto Gomes', '99839149059', 102);
