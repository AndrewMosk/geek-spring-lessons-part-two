CREATE TABLE brands_categories (
  brand_id BIGINT NOT NULL,
  category_id BIGINT NOT NULL,
  PRIMARY KEY (brand_id, category_id)
) ENGINE=InnoDB
GO

ALTER TABLE brands_categories
    ADD INDEX 2_idx (category_id)
GO

ALTER TABLE brands_categories
    ADD CONSTRAINT brands_categories_brand_id
    FOREIGN KEY (brand_id)
    REFERENCES brands (id)
GO

ALTER TABLE brands_categories
    ADD CONSTRAINT brands_categories_category_id
    FOREIGN KEY (category_id)
    REFERENCES categories (id)
GO
