create schema IF NOT EXISTS mcbank;

use mcbank;


drop table if exists users;
drop table if exists accounts;
drop table if exists transactions;

CREATE TABLE users
(
    id            BIGINT IDENTITY (1,1) PRIMARY KEY,
    name          VARCHAR(250),
    surname       VARCHAR(250),
    is_admin       boolean,
    username      varchar(250),
    password      varchar(250),
    authorization varchar(20) unique
);



CREATE TABLE accounts
(
    id            BIGINT IDENTITY (1,1) PRIMARY KEY,
    amount        DOUBLE,
    creation_date DATE,
    type          INT,
    user_id       BIGINT
);

CREATE TABLE transactions
(
    id               BIGINT IDENTITY (1,1) PRIMARY KEY,
    amount           DOUBLE,
    type             INT,
    transaction_date DATE,
    account_id       BIGINT
);


insert into users (id, name, surname, is_admin, username, password, authorization)
values (1, 'Mihaela', 'Cocean', true, 'mihaela.cocean', 'pass4miha', 'rfqqvv63vdw');
insert into users (id, name, surname, is_admin, username, password, authorization)
values (2, 'John', 'John', false, 'john.john', 'pass4john', 'jkahjdkhfkvo658');
insert into users (id, name, surname, is_admin, username, password, authorization)
values (3, 'Mary', 'Sullivan',false, 'mary.sullivan', 'pass4mary', 'lqopjffjqij1&sm)gss');
insert into users (id, name, surname, is_admin, username, password, authorization)
values (4, 'Lisa', 'Sullivan',false, 'lisa.sullivan', 'pass4lisa', 'jkqkbfbsl63!mjd');
insert into users (id, name, surname, is_admin, username, password, authorization)
values (5, 'Admin', 'Admin', true, 'admin', 'admin', 'owjdwkldfqj@6dm');


INSERT INTO accounts(id, amount, creation_date, type, user_id)
values (1, 345, now(), 0, 1);
INSERT INTO accounts(id, amount, creation_date, type, user_id)
values (2, 100, now(), 1, 1);
INSERT INTO accounts(id, amount, creation_date, type, user_id)
values (3, 3442, now(), 0, 2);


insert into transactions(id, amount, type, transaction_date, account_id)
values (1, 565, 0, NOW(), 1);
insert into transactions(id, amount, type, transaction_date, account_id)
values (2, 20, 1, NOW(), 1);
insert into transactions(id, amount, type, transaction_date, account_id)
values (3, 200, 1, NOW(), 1);
insert into transactions(id, amount, type, transaction_date, account_id)
values (4, 100, 0, NOW(), 2);
insert into transactions(id, amount, type, transaction_date, account_id)
values (5, 3442, 0, NOW(), 3);
