package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.geekbrains.repo.BrandRepository;
import ru.geekbrains.repo.CategoryRepository;
import ru.geekbrains.service.ProductService;


@Controller
public class ProductsController {

    private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);

    private final ProductService productService;

//    private final CategoryRepository categoryRepository;
//
//    private final BrandRepository brandRepository;

    @Autowired
    public ProductsController(ProductService productService, CategoryRepository categoryRepository,
                              BrandRepository brandRepository) {
        this.productService = productService;
//        this.categoryRepository = categoryRepository;
//        this.brandRepository = brandRepository;
    }

    @GetMapping("/store")
    public String adminProductsPage(Model model) {
        model.addAttribute("activePage", "Products");
        model.addAttribute("products", productService.findAll());
        return "store";
    }

    @GetMapping("/product/{id}/view")
    public String adminEditProduct(Model model, @PathVariable("id") Long id) {
//        model.addAttribute("edit", true);
        model.addAttribute("activePage", "Products");
        model.addAttribute("product", productService.findById(id).orElseThrow(NotFoundException::new));
//        model.addAttribute("categories", categoryRepository.findAll());
//        model.addAttribute("brands", brandRepository.findAll());
        return "product";
    }



}
