CREATE SCHEMA `shop` ;
use shop;
create table if not exists category
(
    category varchar(45) not null
        primary key,
    constraint name_UNIQUE
        unique (category)
);

create table if not exists product
(
    id          int auto_increment
        primary key,
    title       varchar(256)  not null,
    description text          not null,
    price       int           not null,
    image_url   varchar(2048) not null,
    model_year  int           not null,
    in_stock    int           not null,
    category    varchar(45)   not null,
    state       varchar(45)   not null,
    constraint id_UNIQUE
        unique (id),
    constraint category_fk
        foreign key (category) references category (category)
            on update cascade on delete cascade
);

create index category_id_idx
    on product (category);

create table if not exists user
(
    id           int auto_increment
        primary key,
    login        varchar(45) not null,
    name         varchar(45) not null,
    surname      varchar(45) not null,
    phone_number int         not null,
    email        varchar(45) not null,
    role         int         not null,
    password     varchar(45) not null,
    locale       varchar(45) not null,
    constraint email_UNIQUE
        unique (email),
    constraint iduser_UNIQUE
        unique (id),
    constraint login_UNIQUE
        unique (login)
);

create table if not exists ordercol
(
    id         int auto_increment
        primary key,
    user_id    int         not null,
    status     varchar(45) not null,
    order_date varchar(45) not null,
    constraint user_id
        foreign key (user_id) references user (id)
            on update cascade on delete cascade
);

create table if not exists order_items
(
    order_id   int not null,
    product_id int not null,
    quantity   int not null,
    constraint order_id
        foreign key (order_id) references ordercol (id)
            on update cascade on delete cascade,
    constraint product_id
        foreign key (product_id) references product (id)
            on update cascade on delete cascade
);

create index item_id_idx
    on order_items (product_id);

create index order_id_idx
    on order_items (order_id);

create index user_id_idx
    on ordercol (user_id);

