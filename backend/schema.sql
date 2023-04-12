drop database if exists tradingjournal;

create database tradingjournal;

use tradingjournal;

drop table if exists users;

create table users (

    name varchar(128) not null,
    email varchar(128) not null,
    password varchar(64) not null,
    role enum("USER", "ADMIN"),

    primary key (email)

);

drop table if exists days;

create table days (

    day_id varchar(8),
    pnl decimal(10,2),
    email varchar(128) not null,
    day date,

    primary key(day_id),
    

    constraint fk_email
        foreign key(email)
        references users(email)

);

select * from users;

select * from days;