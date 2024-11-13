insert into USUARIOS (id, username, password, role) values (100, 'ana@email.com', '$2a$12$aDxVS1vfLc4k9I0fVrjYhuCtEvUrXbacnWio9KovPv4bCikDC7V8G', 'ROLE_ADMIN');

insert into USUARIOS (id, username, password, role) values (101, 'bia@email.com', '$2a$12$aDxVS1vfLc4k9I0fVrjYhuCtEvUrXbacnWio9KovPv4bCikDC7V8G', 'ROLE_CLIENTE');

insert into USUARIOS (id, username, password, role) values (102, 'bob@email.com', '$2a$12$aDxVS1vfLc4k9I0fVrjYhuCtEvUrXbacnWio9KovPv4bCikDC7V8G', 'ROLE_CLIENTE');

insert into CLIENTES (id, nome, cpf, id_usuario) values (21, 'Biatriz Rodrigues', '65434217039', 101);
insert into CLIENTES (id, nome, cpf, id_usuario) values (22, 'Rodrigo Silva', '99839149059', 102);

insert into VAGAS (id, codigo, status) values (100, 'A-03', 'OCUPADA');
insert into VAGAS (id, codigo, status) values (200, 'A-04', 'OCUPADA');
insert into VAGAS (id, codigo, status) values (300, 'A-05', 'OCUPADA');
insert into VAGAS (id, codigo, status) values (400, 'A-06', 'LIVRE');
insert into VAGAS (id, codigo, status) values (500, 'A-07', 'LIVRE');

insert into clientes_tem_vagas (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga) values ('20230313-101300', 'FIT-1020', 'FIAT', 'PALIO', 'VERDE','2023-03-13 10:15:00', 22, 100);
insert into clientes_tem_vagas (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga) values ('20230314-101400', 'SIE-1020', 'FIAT','SIENA' 'BRANCO', '2023-03-13 10:15:00', 21, 200);
insert into clientes_tem_vagas (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga) values ('20230315-101500', 'FIT-1020', 'FIAT', 'PALIO','VERDE', '2023-03-13 10:15:00', 22, 300);
