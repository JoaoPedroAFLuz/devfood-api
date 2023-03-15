insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Italiana');

insert into restaurante (nome, taxa_entrega, cozinha_id) value ('Seu Zé', 7.50, 1);
insert into restaurante (nome, taxa_entrega, cozinha_id) value ('Dona Maria', 5.50, 2);

insert into forma_pagamento (descricao) value ('Cartão de crédito');
insert into forma_pagamento (descricao) value ('Cartão de débito');
insert into forma_pagamento (descricao) value ('PIX');
insert into forma_pagamento (descricao) value ('Dinheiro');

insert into estado (id, nome, sigla) values (1, 'Bahia', 'BA');

insert into cidade (nome, estado_id) values ('Vitória da Conquista', 1);