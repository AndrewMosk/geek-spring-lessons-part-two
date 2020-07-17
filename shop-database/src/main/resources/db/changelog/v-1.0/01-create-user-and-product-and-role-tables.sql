-- create table brands (
--        id bigint not null auto_increment,
--         name varchar(255) not null,
--         primary key (id)
--     ) engine=InnoDB
-- GO
--
--     create table categories (
--        id bigint not null auto_increment,
--         name varchar(255) not null,
--         parent_id bigint,
--         primary key (id)
--     ) engine=InnoDB
-- GO
--
--     create table pictures (
--        id bigint not null auto_increment,
--         content_type varchar(255) not null,
--         name varchar(255) not null,
--         picture_data_id bigint not null,
--         product_id bigint not null,
--         primary key (id)
--     ) engine=InnoDB
-- GO
--
--     create table pictures_data (
--        id bigint not null auto_increment,
--         data longblob not null,
--         primary key (id)
--     ) engine=InnoDB
-- GO
--
--     create table products (
--        id bigint not null auto_increment,
--         cost decimal(19,2),
--         name varchar(64),
--         brand_id bigint not null,
--         category_id bigint not null,
--         primary key (id)
--     ) engine=InnoDB
-- GO

    create table roles (
       id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB
GO

    create table users (
       id bigint not null auto_increment,
        age integer,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        name varchar(32),
        password varchar(128),
        primary key (id)
    ) engine=InnoDB
GO

    create table users_roles (
       user_id bigint not null,
        role_id bigint not null,
        primary key (user_id, role_id)
    ) engine=InnoDB
GO

--     alter table brands
--        add constraint UK_oce3937d2f4mpfqrycbr0l93m unique (name)
-- GO
--
--     alter table categories
--        add constraint UK_t8o6pivur7nn124jehx7cygw5 unique (name)
-- GO
--
--     alter table pictures
--        add constraint UK_ehsu2tyinopypjox1ijxt3g3c unique (picture_data_id)
-- GO

    alter table roles
       add constraint UK_ofx66keruapi6vyqpv6f2or37 unique (name)
GO

--     alter table pictures
--        add constraint FKe9cv52k04xoy6cj8xy308gnw3
--        foreign key (picture_data_id)
--        references pictures_data (id)
-- GO
--
--     alter table pictures
--        add constraint FK43hu51t487tsmo7tltxmdx9br
--        foreign key (product_id)
--        references products (id)
-- GO
--
--     alter table products
--        add constraint FKa3a4mpsfdf4d2y6r8ra3sc8mv
--        foreign key (brand_id)
--        references brands (id)
-- GO
--
--     alter table products
--        add constraint FKog2rp4qthbtt2lfyhfo32lsw9
--        foreign key (category_id)
--        references categories (id)
-- GO

    alter table users_roles
       add constraint FKj6m8fwv7oqv74fcehir1a9ffy
       foreign key (role_id)
       references roles (id)
GO

    alter table users_roles
       add constraint FK2o0jvgh89lemvvo17cbqvdxaa
       foreign key (user_id)
       references users (id)
GO