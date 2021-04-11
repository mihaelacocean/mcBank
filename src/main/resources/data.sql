create schema IF NOT EXISTS mcbank;

use mcbank;


drop table if exists users;
drop table if exists accounts;
drop table if exists transactions;
drop table if exists users_accounts;
drop table if exists accounts_transactions;

CREATE TABLE users (
  id BIGINT IDENTITY(1,1) PRIMARY KEY,
  name VARCHAR(250),
  surname VARCHAR(250) );



CREATE TABLE accounts (
  id BIGINT IDENTITY(1,1) PRIMARY KEY,
  amount DOUBLE ,
  creation_date DATE ,
  type INT,
  user_id BIGINT);

CREATE TABLE transactions (
 id BIGINT IDENTITY(1,1) PRIMARY KEY,
  amount DOUBLE ,
  type INT,
  transaction_date DATE,
 account_id BIGINT);


insert into users (id, name, surname) values (1, 'Mihaela', 'Cocean');
insert into users (id, name, surname) values (2, 'John', 'John');
insert into users (id, name, surname) values (3, 'Mary', 'Sullivan');
insert into users (id, name, surname) values (4, 'Lisa', 'Sullivan');


INSERT INTO accounts(id, amount, creation_date, type, user_id) values (1, 345, now(), 1,1);
INSERT INTO accounts(id, amount, creation_date, type, user_id) values (2, 10, now(), 2,1);
INSERT INTO accounts(id, amount, creation_date, type, user_id) values (3, 34442, now(), 2,2);


insert into transactions(id, amount, type, transaction_date, account_id) values (1, 20,1, NOW(),1);
insert into transactions(id, amount, type, transaction_date, account_id) values (2, 200,1, NOW(),1);
insert into transactions(id, amount, type, transaction_date,  account_id) values (3, 34,1, NOW(), 2);
