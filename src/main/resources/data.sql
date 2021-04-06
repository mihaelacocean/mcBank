create schema IF NOT EXISTS mcbank;

use mcbank;


drop table if exists users;

CREATE TABLE users (
  id BIGINT not null unique,
  name VARCHAR(250),
  surname VARCHAR(250),
  balance double);


insert into users (id, name, surname, balance) values (1, 'Mihaela', 'Cocean', 13342.6);
insert into users (id, name, surname, balance) values (2, 'John', 'John', 1245.0);
insert into users (id, name, surname, balance) values (3, 'Mary', 'Sullivan', 344);
insert into users (id, name, surname, balance) values (4, 'Lisa', 'Sullivan', 100);