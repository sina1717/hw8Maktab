create table if not exists category(
                                       id serial primary key ,
                                       title varchar(255),
    description text,
    parent_id int,
    constraint fk_parent foreign key (parent_id)references category(id)
    );
create table if not exists product(
                                      id serial primary key ,
                                      name varchar(255),
    description text,
    category_id int ,
    qty int,
    price int,
    constraint fk_category foreign key (category_id) references category(id)
    );
create table if not exists customer(
                                       id serial primary key ,
                                       username varchar(255),
    password varchar(255),
    address varchar(511)
    );
create table if not exists admins(
                                     id serial primary key ,
                                     username varchar(255),
    password varchar(255),
    national_code char(10)
    );
create table if not exists shopping_card(
                                            id serial primary key ,
                                            date date,
                                            is_payed boolean
);
create table if not exists orders (
                                      id serial primary key ,
                                      product_id int,
                                      customer_id int,
                                      shopping_card_id int,
                                      constraint fk_product foreign key (product_id) references product(id),
    constraint fk_customer foreign key (product_id) references customer(id),
    constraint fk_shopping_card foreign key (shopping_card_id) references shopping_card(id)
    );
insert into category (title, description) VALUES ('all category','all');
insert into admins(username, password, national_code) VALUES ('admin','admin','1234567890');