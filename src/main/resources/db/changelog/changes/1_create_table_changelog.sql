create table users(
    id serial primary key ,
    username varchar(100) not null unique ,
    password varchar not null ,
    firstname varchar(50) not null ,
    lastname varchar(50) not null ,
    passport_no varchar(60) not null,
    birth_date date not null ,
    role varchar(10) not null
);

create table card(
    id serial primary key ,
    card_number varchar(20) not null unique ,
    expire_date timestamp not null ,
    card_status varchar(10),
    balance decimal,
    user_id int,
    constraint fk_user foreign key(user_id) references users(id)
);