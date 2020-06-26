    alter table roles
       drop index UK_ofx66keruapi6vyqpv6f2or37;
    GO

    alter table users_roles
       drop constraint FKj6m8fwv7oqv74fcehir1a9ffy;
    GO

    alter table users_roles
       drop constraint FK2o0jvgh89lemvvo17cbqvdxaa;
    GO

    alter table categories
       drop index UK_t8o6pivur7nn124jehx7cygw5;
    GO

    alter table products_categories
       drop constraint FKd112rx0alycddsms029iifrih;
    GO

    alter table products_categories
       drop constraint FKlda9rad6s180ha3dl1ncsp8n7;
    GO

    drop table roles;
    GO

    drop table users;
    GO

    drop table users_roles;
    GO

    drop table categories;
    GO

    drop table products;
    GO

    drop table products_categories;
    GO