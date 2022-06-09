create table IF NOT EXISTS category
(
    id   int         not null
        primary key,
    name varchar(45) not null,
    constraint name_UNIQUE
        unique (name)
);

create table IF NOT EXISTS product
(
    id          int auto_increment
        primary key,
    title       varchar(256)   not null,
    description text           not null,
    price       decimal(10, 2) not null,
    image_url   varchar(2048)  not null,
    model_year  int            not null,
    in_stock    int            not null,
    category_id int            not null,
    `condition` varchar(45)    not null,
    constraint id_UNIQUE
        unique (id)
);

create table IF NOT EXISTS user
(
    id           int auto_increment
        primary key,
    login        varchar(45) not null,
    name         varchar(45) not null,
    surname      varchar(45) not null,
    phone_number int         not null,
    email        varchar(45) not null,
    role         varchar(45) not null,
    password     varchar(45) not null,
    locale       varchar(45) not null,
    constraint iduser_UNIQUE
        unique (id),
    constraint login_UNIQUE
        unique (login)
);

create table IF NOT EXISTS `order`
(
    id         int auto_increment
        primary key,
    user_id    int         not null,
    status     varchar(45) not null,
    order_date varchar(45) not null,
    constraint user_id
        foreign key (user_id) references user (id)
            on update cascade
);

create index  user_id_idx
    on `order` (user_id);

create table IF NOT EXISTS order_items
(
    order_id   int            not null,
    product_id int            not null,
    quantity   int            not null,
    price      decimal(10, 2) not null,
    constraint order_id
        foreign key (order_id) references `order` (id)
            on update cascade,
    constraint product_id
        foreign key (product_id) references product (id)
            on update cascade
);

create index item_id_idx
    on order_items (product_id);

create index order_id_idx
    on order_items (order_id);

