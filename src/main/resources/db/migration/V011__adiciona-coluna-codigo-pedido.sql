ALTER TABLE pedido
    ADD codigo VARCHAR(36) NOT NULL AFTER id;

UPDATE pedido
SET pedido.codigo = UUID();

ALTER TABLE pedido
    ADD CONSTRAINT uk_pedido_codigo UNIQUE (codigo);