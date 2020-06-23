    create table products (
       id bigint not null auto_increment,
        cost decimal(19,2),
        title varchar(64),
        primary key (id)
    ) engine=InnoDB

    create table roles (
       id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB

    create table users (
       id bigint not null auto_increment,
        age integer,
        email varchar(255),
        name varchar(32),
        password varchar(128),
        primary key (id)
    ) engine=InnoDB

    create table users_roles (
       user_id bigint not null,
        role_id bigint not null,
        primary key (user_id, role_id)
    ) engine=InnoDB

    alter table roles
       drop index UK_ofx66keruapi6vyqpv6f2or37

    alter table roles
       add constraint UK_ofx66keruapi6vyqpv6f2or37 unique (name)

    alter table users_roles
       add constraint FKj6m8fwv7oqv74fcehir1a9ffy
       foreign key (role_id)
       references roles (id)

    alter table users_roles
       add constraint FK2o0jvgh89lemvvo17cbqvdxaa
       foreign key (user_id)
       references users (id)
