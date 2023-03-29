insert into estado (id, nome, sigla) values (1, 'Bahia', 'BA');
insert into estado (id, nome, sigla) values (2, 'Ceará', 'CE');
insert into estado (id, nome, sigla) values (3, 'Minas Gerais', 'MG');

insert into cidade (nome, estado_id) values ('Vitória da Conquista', 1);
insert into cidade (nome, estado_id) values ('Juazeiro do Norte', 2);
insert into cidade (nome, estado_id) values ('Belo Horizonte', 3);

insert into forma_pagamento (descricao) values ('Cartão de crédito');
insert into forma_pagamento (descricao) values ('Cartão de débito');
insert into forma_pagamento (descricao) values ('PIX');
insert into forma_pagamento (descricao) values ('Dinheiro');

insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Italiana');

insert into restaurante (nome, taxa_entrega, cozinha_id, data_cadastro, data_atualizacao, endereco_cep, endereco_cidade_id, endereco_bairro, endereco_complemento, endereco_logradouro, endereco_numero) values ('Seu Zé', 7.50, 1, utc_timestamp, utc_timestamp, '4500500', 1, 'Candeias', 'Casa', null, '96');
insert into restaurante (nome, taxa_entrega, cozinha_id, data_cadastro, data_atualizacao, endereco_cep, endereco_cidade_id, endereco_bairro, endereco_complemento, endereco_logradouro, endereco_numero) values ('Dona Maria', 5.50, 2, utc_timestamp, utc_timestamp, '4500500', 1, 'Recreio', 'Apartamento', null, '500');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (1, 4), (2, 1), (2, 2), (2, 4);
