package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.model.Product;
import ru.geekbrains.repo.ProductRepository;


import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return repository.findAll();
    }

    public Page<Product> filterByParams(String name, Boolean minPrice, Boolean maxPrice, Pageable pageable) {
        if (!name.isEmpty()) {
            return repository.findByNameContains(name, pageable);
        }

        if (minPrice && maxPrice) {
            return repository.findByMinAndMaxPrice(pageable);
        } else if (minPrice) {
            return repository.findByMinPrice(pageable);
        } else if (maxPrice) {
            return repository.findByMaxPrice(pageable);
        }

        return repository.findAll(pageable);
    }

    @Transactional
    public void save(Product product) {
        repository.save(product);
    }

    @Transactional(readOnly = true)
    public Optional<Product> findById(long id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
