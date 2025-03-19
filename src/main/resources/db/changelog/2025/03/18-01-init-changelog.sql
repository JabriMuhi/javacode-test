-- liquibase formatted sql

-- changeset jabrimuhi:1742293726674-1
CREATE TABLE wallet
(
    id      UUID   NOT NULL,
    balance BIGINT NOT NULL,
    CONSTRAINT pk_wallet PRIMARY KEY (id)
);

