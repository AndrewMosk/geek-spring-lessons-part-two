    alter table roles
       drop index UK_ofx66keruapi6vyqpv6f2or37;
    GO

    alter table users_roles
       drop constraint FKj6m8fwv7oqv74fcehir1a9ffy;
    GO

    alter table users_roles
       drop constraint FK2o0jvgh89lemvvo17cbqvdxaa;
    GO

    drop table products;
    GO

    drop table roles;
    GO

    drop table users;
    GO

    drop table users_roles;
    GO