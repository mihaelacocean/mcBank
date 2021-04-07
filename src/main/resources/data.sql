create schema IF NOT EXISTS mcbank;

use mcbank;


drop table if exists users;
drop table if exists accounts;
drop table if exists transactions;
drop table if exists users_accounts;
drop table if exists accounts_transactions;

CREATE TABLE users (
  id BIGINT not null unique,
  name VARCHAR(250),
  surname VARCHAR(250),
  balance DOUBLE );



CREATE TABLE accounts (
  id BIGINT not null unique,
  amount DOUBLE ,
  creation_date DATE ,
  type INT,
  user_id BIGINT);

create table users_accounts (
    user_id BIGINT,
    accounts_id BIGINT
);

CREATE TABLE transactions (
 id BIGINT not null unique,
  amount DOUBLE ,
  type INT,
  transaction_date DATE,
 account_id BIGINT);

create table accounts_transactions (
                                     account_id BIGINT,
   transactions_id BIGINT
);

insert into users (id, name, surname, balance) values (1, 'Mihaela', 'Cocean', 13342.6);
insert into users (id, name, surname, balance) values (2, 'John', 'John', 1245.0);
insert into users (id, name, surname, balance) values (3, 'Mary', 'Sullivan', 344);
insert into users (id, name, surname, balance) values (4, 'Lisa', 'Sullivan', 100);


INSERT INTO accounts(id, amount, creation_date, type, user_id) values (1, 345, now(), 1,1);
INSERT INTO accounts(id, amount, creation_date, type, user_id) values (2, 10, now(), 2,1);
INSERT INTO accounts(id, amount, creation_date, type, user_id) values (3, 34442, now(), 2,1);

insert into users_accounts values(1,1);
insert into users_accounts values(1,2);
insert into users_accounts values(2,3);

insert into transactions(id, amount, type, transaction_date, account_id) values (1, 20,1, NOW(),1);
insert into transactions(id, amount, type, transaction_date, account_id) values (2, 200,1, NOW(),1);
insert into transactions(id, amount, type, transaction_date,  account_id) values (3, 34,1, NOW(), 2);

insert into accounts_transactions values(1,1);
insert into accounts_transactions values(1,2);
insert into accounts_transactions values(2,3);