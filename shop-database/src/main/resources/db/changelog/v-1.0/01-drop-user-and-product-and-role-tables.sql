-- users -> users_roles -> roles
    ALTER TABLE `roles` DROP KEY `UK_ofx66keruapi6vyqpv6f2or37`;
GO

    ALTER TABLE `users_roles` DROP FOREIGN KEY `FKj6m8fwv7oqv74fcehir1a9ffy`;
GO

    ALTER TABLE `users_roles` DROP FOREIGN KEY `FK2o0jvgh89lemvvo17cbqvdxaa`;
GO

    DROP TABLE `roles`;
GO

    DROP TABLE `users_roles`;
GO

    DROP TABLE `users`;
GO

-- products -> categories -> brands -> pictures -> pictures_data
    ALTER TABLE `brands` DROP KEY `UK_oce3937d2f4mpfqrycbr0l93m`;
GO

    ALTER TABLE `categories` DROP KEY `UK_t8o6pivur7nn124jehx7cygw5`;
GO

    ALTER TABLE `pictures` DROP FOREIGN KEY `FKe9cv52k04xoy6cj8xy308gnw3`;
GO

    ALTER TABLE `pictures` DROP FOREIGN KEY `FK43hu51t487tsmo7tltxmdx9br`;
GO

    ALTER TABLE `pictures` DROP KEY `UK_ehsu2tyinopypjox1ijxt3g3c`;
GO

    ALTER TABLE `products` DROP FOREIGN KEY `FKa3a4mpsfdf4d2y6r8ra3sc8mv`;
GO

    ALTER TABLE `products` DROP FOREIGN KEY `FKog2rp4qthbtt2lfyhfo32lsw9`;
GO

    DROP TABLE `brands`;
GO

    DROP TABLE `categories`;
GO

    DROP TABLE `pictures_data`;
GO

    DROP TABLE `pictures`;
GO

    DROP TABLE `products`;
GO