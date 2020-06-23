package repo;

import model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import javax.validation.constraints.NotBlank;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByTitle(String name);

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

    Page<Product> findByTitleContains(@NotBlank String title, Pageable pageable);
}