CREATE TABLE `spring_shop_db_2`.`brands_catgories` (
  `brand_id` BIGINT NOT NULL,
  `category_id` BIGINT NOT NULL,
  PRIMARY KEY (`brand_id`, `category_id`));


ALTER TABLE `spring_shop_db_2`.`brands_catgories`
ADD INDEX `2_idx` (`category_id` ASC) VISIBLE;
;
ALTER TABLE `spring_shop_db_2`.`brands_catgories`
ADD CONSTRAINT `1`
  FOREIGN KEY (`brand_id`)
  REFERENCES `spring_shop_db_2`.`brands` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `2`
  FOREIGN KEY (`category_id`)
  REFERENCES `spring_shop_db_2`.`categories` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
