--create table
create table products (
           id bigint not null auto_increment,
            base_unit varchar(255),
            is_active bit not null,
            name varchar(255),
            price double precision not null,
            quantity double precision not null,
            primary key (id)
        );

--Insert dummy values

insert into products (base_unit, is_active, name, price, quantity) values ('unit',true, 'item1', 10, 260);
insert into products (base_unit, is_active, name, price, quantity) values ('gram',true, 'item2', 100, 120);
insert into products (base_unit, is_active, name, price, quantity) values ('kg',true, 'item3', 40, 20);
insert into products (base_unit, is_active, name, price, quantity) values ('unit',true, 'item4', 70, 820);
insert into products (base_unit, is_active, name, price, quantity) values ('unit',true, 'item5', 20, 620);
insert into products (base_unit, is_active, name, price, quantity) values ('unit',true, 'item6', 80, 520);
insert into products (base_unit, is_active, name, price, quantity) values ('unit',true, 'item7', 30, 240);
insert into products (base_unit, is_active, name, price, quantity) values ('unit',true, 'item8', 160, 320);
insert into products (base_unit, is_active, name, price, quantity) values ('unit',true, 'item9', 140, 210);
insert into products (base_unit, is_active, name, price, quantity) values ('unit',true, 'item10', 140, 20);