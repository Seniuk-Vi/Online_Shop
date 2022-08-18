DROP DATABASE if exists shoptest;
CREATE SCHEMA  if not exists shoptest;
use shoptest;
create table if not exists shoptest.category
(
    category varchar(45) not null
        primary key,
    constraint name_UNIQUE
        unique (category)
);
INSERT  INTO `shoptest`.`category`(`category`)
VALUES ('Car');

INSERT INTO `shoptest`.`category`(`category`)
VALUES ('Toy');

INSERT INTO `shoptest`.`category`(`category`)
VALUES ('Phone');

create table if not exists shoptest.product
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
        foreign key (category) references shoptest.category (category)
            on update cascade on delete cascade
);

create index category_id_idx
    on shoptest.product (category);

INSERT INTO shoptest.product (title, description, price, image_url, model_year, in_stock, category, state)
VALUES ('Mercedes w201',
        ' Авто обслужене, всі рідини , гальмівна система замінені. Їздить кожного дня. Потребує зварювальних робіт. Передні, задні крила, двері, піддонкратники віддаю. Кондиціонер. Розхід 6-7л. ',
        6700.0, 'w201.jpg', 1987, 1, 'Car', '8');
INSERT INTO shoptest.product (title, description, price, image_url, model_year, in_stock, category, state)
VALUES ('Прозорий Слайм 150 мл',
        ' У баночці 150 мл супер прозорого слайма. Не липне до рук, тягнеться, рветься, хрумтить і клікає - відмінна якість\r\n\r\nСлайм виконаний з клею elmer''s\r\n\r\nКлейElmer''s Застосовується для творчості, хобі та ремесел, школи. Дуже популярний у всьому світі для виготовлення слаймов (лізунов) з-за відповідної консистенції і безпеки (ACMI сертифікація).\r\n\r\nНе жовтіє! Легко витирається водою, засохлий ',
        5.0, 'toy.jpg', 2022, 222, 'Toy', '10');
INSERT INTO shoptest.product (title, description, price, image_url, model_year, in_stock, category, state)
VALUES ('Pepsi phone',
        'Відчуйте, що відбувається на екрані, якщо б ви були очевидцем подій. Оцініть всі переваги технології Dynamic AMOLED на імерсивному 6,1 - дюймовому екрані з роздільною здатністю Quad HD +. Навіть найтемніші сцени виглядають чітко і контрастно. Удосконалена технологія передачі кольору дає можливість з комфортом користуватися смартфоном на яскравому сонці.',
        1000.0, 'phone.jpg', 2019, 222, 'Phone', '10');

create table if not exists shoptest.user
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
INSERT INTO `shoptest`.`user`(`id`, `login`, `name`, `surname`, `phone_number`, `email`, `role`, `password`, `locale`)
VALUES (1, 'login1', 'Tom', 'Handsome', 0635848852, 'tomemail@gmail.com', 0, 'tompassword51', 'uk');

INSERT INTO `shoptest`.`user` (`id`, `login`, `name`, `surname`, `phone_number`, `email`, `role`, `password`, `locale`)
VALUES (2, 'login2', 'Bob', 'Poor', 0897515577, 'bobemail@gmail.com', 1, 'bobpassword51', 'en');

INSERT INTO `shoptest`.`user` (`id`, `login`, `name`, `surname`, `phone_number`, `email`, `role`, `password`, `locale`)
VALUES (3, 'login3', 'Cate', 'Geel', 0347515271, 'cateemail@gmail.com', 2, 'catepassword51', 'en');


create table if not exists shoptest.ordercol
(
    id         int auto_increment
        primary key,
    user_id    int         not null,
    status     varchar(45) not null,
    order_date varchar(45) not null,
    constraint user_id
        foreign key (user_id) references shoptest.user (id)
            on update cascade on delete cascade
);

create table if not exists shoptest.order_items
(
    order_id   int not null,
    product_id int not null,
    quantity   int not null,
    constraint order_id
        foreign key (order_id) references shoptest.ordercol (id)
            on update cascade on delete cascade,
    constraint product_id
        foreign key (product_id) references shoptest.product (id)
            on update cascade on delete cascade
);

create index item_id_idx
    on shoptest.order_items (product_id);

create index order_id_idx
    on shoptest.order_items (order_id);

create index user_id_idx
    on shoptest.ordercol (user_id);

