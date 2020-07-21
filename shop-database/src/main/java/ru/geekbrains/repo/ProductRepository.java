package ru.geekbrains.repo;

import ru.geekbrains.model.Brand;
import ru.geekbrains.model.Category;
import ru.geekbrains.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByName(String name);

    void deleteById(Long id);

    @Query(value = "SELECT * FROM Products WHERE cost = (SELECT MIN(cost) FROM Products)",
            nativeQuery = true)
    Page<Product> findByMinPrice(Pageable pageable);

    @Query(value = "SELECT * FROM Products WHERE cost = (SELECT MAX(cost) FROM Products)",
            nativeQuery = true)
    Page<Product> findByMaxPrice(Pageable pageable);

    @Query(value = "(SELECT * FROM Products p ORDER BY p.cost ASC LIMIT 1) UNION (SELECT * FROM Products p ORDER BY p.cost DESC LIMIT 1)",
            nativeQuery = true)
    Page<Product> findByMinAndMaxPrice(Pageable pageable);

    Page<Product> findByNameContains(@NotBlank String name, Pageable pageable);

    //List<Product> findProductByCategory_Name(String category_name);

    @Query(value = "select * from products where category_id in ( select id from ( \n" +
            "with recursive cte (id, name, parent_id) as (   select\n" +
            "        id,\n" +
            "        name,\n" +
            "        parent_id   \n" +
            "    from\n" +
            "        categories   \n" +
            "    where\n" +
            "        name = ?1" +
            "    union\n" +
            "    all   select\n" +
            "        c.id,\n" +
            "        c.name,\n" +
            "        c.parent_id   \n" +
            "    from\n" +
            "        categories c   \n" +
            "    inner join\n" +
            "        cte           \n" +
            "            on c.parent_id = cte.id ) select\n" +
            "            * \n" +
            "    from\n" +
            "        cte order by id) as c)", nativeQuery = true)
    List<Product> ProductByCategory_Name_Hierarchy(String category_name);

    @Query(value = "select * from products where category_id in ( select id from ( \n" +
            "with recursive cte (id, name, parent_id) as (   select\n" +
            "        id,\n" +
            "        name,\n" +
            "        parent_id   \n" +
            "    from\n" +
            "        categories   \n" +
            "    where\n" +
            "        name = ?1  \n" +
            "    union\n" +
            "    all   select\n" +
            "        c.id,\n" +
            "        c.name,\n" +
            "        c.parent_id   \n" +
            "    from\n" +
            "        categories c   \n" +
            "    inner join\n" +
            "        cte           \n" +
            "            on c.parent_id = cte.id ) select\n" +
            "            * \n" +
            "    from\n" +
            "        cte order by id) as c) and brand_id in (select id from brands where name in (?2))", nativeQuery = true)
    List<Product> ProductByCategory_Name_Hierarchy_And_Brands(String category_name, List<String> brands);

    List<Product> findProductByBrandIn(List<Brand> brand);
}