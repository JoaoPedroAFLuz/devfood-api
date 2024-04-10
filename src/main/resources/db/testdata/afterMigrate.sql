SET foreign_key_checks = 0;

DELETE
FROM cidade;
DELETE
FROM cozinha;
DELETE
FROM estado;
DELETE
FROM forma_pagamento;
DELETE
FROM grupo;
DELETE
FROM grupo_permissao;
DELETE
FROM permissao;
DELETE
FROM produto;
DELETE
FROM restaurante;
DELETE
FROM restaurante_forma_pagamento;
DELETE
FROM usuario;
DELETE
FROM usuario_grupo;
DELETE
FROM restaurante_usuario_responsavel;
DELETE
FROM pedido;
DELETE
FROM item_pedido;

SET foreign_key_checks = 1;

ALTER TABLE cidade
    AUTO_INCREMENT = 1;
ALTER TABLE cozinha
    AUTO_INCREMENT = 1;
ALTER TABLE estado
    AUTO_INCREMENT = 1;
ALTER TABLE forma_pagamento
    AUTO_INCREMENT = 1;
ALTER TABLE grupo
    AUTO_INCREMENT = 1;
ALTER TABLE permissao
    AUTO_INCREMENT = 1;
ALTER TABLE produto
    AUTO_INCREMENT = 1;
ALTER TABLE restaurante
    AUTO_INCREMENT = 1;
ALTER TABLE usuario
    AUTO_INCREMENT = 1;

INSERT INTO cozinha (id, nome)
VALUES (1, 'Brasileira'),
       (2, 'Tailandesa'),
       (3, 'Indiana'),
       (4, 'Argentina');

INSERT INTO estado (id, nome, sigla)
VALUES (1, 'Bahia', 'BA'),
       (2, 'Minas Gerais', 'MG'),
       (3, 'Ceará', 'CE');

INSERT INTO cidade (id, nome, estado_id)
VALUES (1, 'Vitória da Conquista', 1),
       (2, 'Salvador', 1),
       (3, 'Uberlândia', 2),
       (4, 'Belo Horizonte', 2),
       (5, 'Fortaleza', 3),
       (6, 'Juazeiro do Norte', 3);

INSERT INTO restaurante (id, nome, taxa_entrega, cozinha_id, ativo, data_cadastro, data_atualizacao, endereco_cidade_id,
                         endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, aberto)
VALUES (1, 'Lanchonete do Brasil Brasileiro', 10, 1, TRUE, UTC_TIMESTAMP, UTC_TIMESTAMP, 1, '38400-999',
        'Rua João Pinheiro',
        '1000',
        'Centro', TRUE);

INSERT INTO restaurante (id, nome, taxa_entrega, cozinha_id, ativo, data_cadastro, data_atualizacao, aberto)
VALUES (2, 'Bar da Maria', 0, 1, TRUE, UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE),
       (3, 'Thai Gourmet', 11, 2, TRUE, UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE),
       (4, 'Thai Delivery', 9.50, 2, TRUE, UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE),
       (5, 'Tuk Tuk Comida Indiana', 15, 3, TRUE, UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE),
       (6, 'Java Steakhouse', 12, 4, TRUE, UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE);

INSERT INTO forma_pagamento (id, descricao)
VALUES (1, 'Cartão de crédito'),
       (2, 'Cartão de débito'),
       (3, 'Dinheiro');

INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 3),
       (3, 2),
       (3, 3),
       (4, 1),
       (4, 2),
       (5, 1),
       (5, 2),
       (6, 3);

INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id)
VALUES (1, 'Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 1),
       (2, 'Coca-Cola 350ml', 'Coca-Cola gelada', 5.5, 1, 1),
       (3, 'Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 2),
       (4, 'Coca-Cola 350ml', 'Coca-Cola gelada', 5, 1, 1),
       (5, 'Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 3),
       (6, 'Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 3),
       (7, 'Salada picante com carne grelhada',
        'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20,
        1, 4),
       (8, 'Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 5),
       (9, 'Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 5),
       (10, 'Bife Ancho',
        'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé',
        79, 1, 6),
       (11, 'T-Bone',
        'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89,
        1, 6);

INSERT INTO grupo (id, nome)
VALUES (1, 'Gerente'),
       (2, 'Vendedor'),
       (3, 'Secretária'),
       (4, 'Cadastrador');

INSERT INTO permissao (id, nome, descricao)
VALUES (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas'),
       (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

INSERT INTO grupo_permissao (grupo_id, permissao_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 2),
       (3, 1);

INSERT INTO usuario (id, nome, email, senha, data_cadastro)
VALUES (1, 'João Pedro', 'joao.pedro.luz@hotmail.com', '12345678', UTC_TIMESTAMP()),
       (2, 'Mirella', 'mirella@hotmail.com', '12345678', UTC_TIMESTAMP());

INSERT INTO usuario_grupo (usuario_id, grupo_id)
VALUES (1, 1),
       (1, 2),
       (1, 4),
       (2, 1),
       (2, 3);

INSERT INTO restaurante_usuario_responsavel
VALUES (1, 1),
       (1, 2);

INSERT INTO pedido (id, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao,
                    subtotal, taxa_entrega, valor_total)
VALUES (1, 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil', 'CRIADO', UTC_TIMESTAMP,
        24.5, 10, 34.5);

INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
VALUES (1, 1, 1, 1, 19, 19, 'Ao ponto'),
       (2, 1, 2, 1, 5.5, 5.5, NULL);


INSERT INTO pedido (id, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao,
                    subtotal, taxa_entrega, valor_total)
VALUES (2, 4, 2, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro', 'CRIADO', UTC_TIMESTAMP, 27, 0, 27);

INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
VALUES (3, 2, 3, 2, 8, 16, 'Com farinha'),
       (4, 2, 4, 2, 5.5, 11, NULL);