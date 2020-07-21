-- CREATE users -> users_roles -> roles
    CREATE TABLE roles (
       id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id)
    ) ENGINE=InnoDB
GO

    CREATE TABLE users (
       id bigint not null auto_increment,
        age integer,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        name varchar(32),
        password varchar(128),
        primary key (id)
    ) ENGINE=InnoDB
GO

    CREATE TABLE users_roles (
       user_id bigint not null,
        role_id bigint not null,
        primary key (user_id, role_id)
    ) ENGINE=InnoDB
GO

-- CREATE products -> categories -> brands -> pictures -> pictures_data
    CREATE TABLE brands (
       id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id)
    ) ENGINE=InnoDB
GO

    CREATE TABLE categories (
       id bigint not null auto_increment,
        name varchar(255) not null,
        parent_id bigint,
        primary key (id)
    ) ENGINE=InnoDB
GO

    CREATE TABLE pictures (
       id bigint not null auto_increment,
        content_type varchar(255) not null,
        name varchar(255) not null,
        picture_data_id bigint not null,
        product_id bigint not null,
        primary key (id)
    ) ENGINE=InnoDB
GO

    CREATE TABLE pictures_data (
       id bigint not null auto_increment,
        data longblob not null,
        primary key (id)
    ) ENGINE=InnoDB
GO

    CREATE TABLE products (
       id bigint not null auto_increment,
        cost decimal(19,2),
        name varchar(64),
        brand_id bigint not null,
        category_id bigint not null,
        primary key (id)
    ) ENGINE=InnoDB
GO

--ALTER users -> users_roles -> roles
    ALTER TABLE roles
       ADD CONSTRAINT UK_ofx66keruapi6vyqpv6f2or37 unique (name)
GO

    ALTER TABLE users_roles
       ADD CONSTRAINT FKj6m8fwv7oqv74fcehir1a9ffy
       FOREIGN KEY (role_id)
       REFERENCES roles (id)
GO

    ALTER TABLE users_roles
       ADD CONSTRAINT FK2o0jvgh89lemvvo17cbqvdxaa
       FOREIGN KEY (user_id)
       REFERENCES users (id)
GO

-- ALTER products -> categories -> brands -> pictures -> pictures_data
    ALTER TABLE brands
       ADD CONSTRAINT UK_oce3937d2f4mpfqrycbr0l93m unique (name)
GO

    ALTER TABLE categories
       ADD CONSTRAINT UK_t8o6pivur7nn124jehx7cygw5 unique (name)
GO

    ALTER TABLE pictures
       ADD CONSTRAINT UK_ehsu2tyinopypjox1ijxt3g3c unique (picture_data_id)
GO
    ALTER TABLE pictures
       ADD CONSTRAINT FKe9cv52k04xoy6cj8xy308gnw3
       FOREIGN KEY (picture_data_id)
       REFERENCES pictures_data (id)
GO

    ALTER TABLE pictures
       ADD CONSTRAINT FK43hu51t487tsmo7tltxmdx9br
       FOREIGN KEY (product_id)
       REFERENCES products (id)
GO

    ALTER TABLE products
       ADD CONSTRAINT FKa3a4mpsfdf4d2y6r8ra3sc8mv
       FOREIGN KEY (brand_id)
       REFERENCES brands (id)
GO

    ALTER TABLE products
       ADD CONSTRAINT FKog2rp4qthbtt2lfyhfo32lsw9
       FOREIGN KEY (category_id)
       REFERENCES categories (id)
GO