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
DELETE
FROM foto_produto;

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
ALTER TABLE pedido
    AUTO_INCREMENT = 1;
ALTER TABLE item_pedido
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
VALUES (1, 'Lanchonete do Brasil Brasileiro', 10, 1, TRUE, UTC_TIMESTAMP, UTC_TIMESTAMP, 1, '45005-000',
        'Rua João Pinheiro', '1000', 'Centro', TRUE);

INSERT INTO restaurante (id, nome, taxa_entrega, cozinha_id, ativo, data_cadastro, data_atualizacao, aberto)
VALUES (2, 'Bar da Maria', 0, 1, TRUE, UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE),
       (3, 'Thai Gourmet', 11, 2, TRUE, UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE),
       (4, 'Thai Delivery', 9.50, 2, TRUE, UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE),
       (5, 'Tuk Tuk Comida Indiana', 15, 3, TRUE, UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE),
       (6, 'Java Steakhouse', 12, 4, TRUE, UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE);

INSERT INTO forma_pagamento (id, descricao, data_atualizacao)
VALUES (1, 'Cartão de crédito', UTC_TIMESTAMP),
       (2, 'Cartão de débito', UTC_TIMESTAMP),
       (3, 'Dinheiro', UTC_TIMESTAMP);

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
        'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 6),
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
VALUES (1, 'João Pedro', 'joao.pedro.luz25@gmail.com', '12345678', UTC_TIMESTAMP()),
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

INSERT INTO pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id,
                    endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status,
                    data_criacao, data_confirmacao, data_encaminhamento, data_entrega, subtotal, taxa_entrega,
                    valor_total)
VALUES (1, '11e02651-0531-4f9c-b903-940a8e354385', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801',
        'Brasil', 'ENTREGUE', '2024-04-01T18:57:43.499-03:00', '2024-04-01T19:01:41.133-03:00',
        '2024-04-01T19:14:41.133-03:00', '2024-04-01T19:18:41.133-03:00', 24.5, 10, 34.5),
       (2, '1d5e5ad2-299b-45be-94f8-a73026c2aa77', 4, 2, 2, 1, '38400-111', 'Rua Acre', '100', 'Casa 2', 'Centro',
        'ENTREGUE', '2024-04-01T19:02:46.485-03:00', '2024-04-01T19:04:02.312-03:00', '2024-04-01T19:34:02.312-03:00',
        '2024-04-01T19:52:02.312-03:00', 27, 0, 27),
       (3, '94027c9a-4cbf-42af-b298-b36dff86eb2d', 6, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801',
        'Brasil', 'ENTREGUE', '2024-04-20T20:36:46.164-03:00', '2024-04-20T20:37:59.466-03:00',
        '2024-04-20T20:47:59.165-03:00', '2024-04-20T20:58:59.423-03:00', 79, 12, 91);

INSERT INTO pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id,
                    endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status,
                    data_criacao, subtotal, taxa_entrega, valor_total)
VALUES (4, '1b89b549-c0e0-47d2-b2a0-5cface0dc972', 5, 1, 2, 1, '38400-111', 'Rua Acre', '100', 'Casa 2', 'Centro',
        'CRIADO', UTC_TIMESTAMP, 43, 15, 58),
       (5, 'ac6ddd5d-fa46-483b-b4be-ab191d403c38', 3, 2, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
        'CRIADO', UTC_TIMESTAMP, 110, 11, 111);

INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
VALUES (1, 1, 1, 1, 19, 19, 'Ao ponto'),
       (2, 1, 2, 1, 5.5, 5.5, NULL),
       (3, 2, 3, 2, 8, 16, 'Com farinha'),
       (4, 2, 4, 2, 5.5, 11, NULL),
       (5, 3, 10, 1, 79, 79, 'Ao Ponto'),
       (6, 4, 9, 1, 43, 43, NULL),
       (7, 5, 6, 1, 110, 110, NULL);