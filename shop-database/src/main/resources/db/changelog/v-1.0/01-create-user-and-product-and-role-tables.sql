create table brands (
       id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB
GO 
    
    create table categories (
       id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB
GO 
    
    create table products (
       id bigint not null auto_increment,
        cost decimal(19,2),
        name varchar(64),
        brand_id bigint not null,
        primary key (id)
    ) engine=InnoDB
GO 
    
    create table products_categories (
       product_id bigint not null,
        category_id bigint not null,
        primary key (product_id, category_id)
    ) engine=InnoDB
GO 
    
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
    
    alter table brands 
       add constraint UK_oce3937d2f4mpfqrycbr0l93m unique (name)
GO 
    
    alter table categories 
       add constraint UK_t8o6pivur7nn124jehx7cygw5 unique (name)
GO 
    
    alter table roles 
       add constraint UK_ofx66keruapi6vyqpv6f2or37 unique (name)
GO 
    
    alter table products 
       add constraint FKa3a4mpsfdf4d2y6r8ra3sc8mv 
       foreign key (brand_id) 
       references brands (id)
GO 
    
    alter table products_categories 
       add constraint FKqt6m2o5dly3luqcm00f5t4h2p 
       foreign key (category_id) 
       references categories (id)
GO 
    
    alter table products_categories 
       add constraint FKtj1vdea8qwerbjqie4xldl1el 
       foreign key (product_id) 
       references products (id)
GO 
    
    alter table users_roles 
       add constraint FKj6m8fwv7oqv74fcehir1a9ffy 
       foreign key (role_id) 
       references roles (id)
GO 
    
    alter table users_roles 
       add constraint FK2o0jvgh89lemvvo17cbqvdxaa 
       foreign key (user_id) 
       references users (id)