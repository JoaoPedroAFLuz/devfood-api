CREATE TABLE cidade
(
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    nome_cidade VARCHAR(80) NOT NULL,
    nome_estado VARCHAR(80) NOT NULL,

    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;