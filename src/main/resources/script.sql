CREATE DATABASE estoque;

CREATE TABLE t_produto (
  id          BIGSERIAL NOT NULL
    CONSTRAINT t_produto_pkey
    PRIMARY KEY,
  name        VARCHAR(255),
  description VARCHAR(500),
  price       NUMERIC(12, 2)
);




