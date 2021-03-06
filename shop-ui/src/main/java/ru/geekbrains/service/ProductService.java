package ru.geekbrains.service;



import ru.geekbrains.controller.repr.ProductRepr;
import ru.geekbrains.model.Brand;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductRepr> findAll();

    Optional<ProductRepr> findById(Long id);

    List<ProductRepr> findByCatName(String cat_name);

    List<ProductRepr> findByCatNameAndBrands(String cat_name, List<String> brands);

    List<ProductRepr> findProductByBrand(List<Brand> brands);
}
