INSERT INTO `users` (`name`, `password`)
VALUE   ('admin', '$2y$12$dCS6mKghk1t5A.dRvjecMeQoCcEgWa27zGaokFoG7OhrQyVEHI0xW'),
        ('guest', '$2y$12$dCS6mKghk1t5A.dRvjecMeQoCcEgWa27zGaokFoG7OhrQyVEHI0xW');
GO

INSERT INTO `roles` (`name`)
VALUE ('ROLE_ADMIN'), ('ROLE_GUEST');
GO

INSERT INTO `users_roles` (`user_id`, `role_id`)
SELECT (SELECT id FROM `users` WHERE `name` = 'admin'), (SELECT id FROM `roles` WHERE `name` = 'ROLE_ADMIN')
UNION ALL
SELECT (SELECT id FROM `users` WHERE `name` = 'guest'), (SELECT id FROM `roles` WHERE `name` = 'ROLE_GUEST');
GO