package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.repr.ProductRepr;
import ru.geekbrains.model.Product;
import ru.geekbrains.repo.BrandRepository;
import ru.geekbrains.repo.CategoryRepository;
import ru.geekbrains.service.NotFoundException;
import ru.geekbrains.service.ProductService;


import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;

@RequestMapping("/product")
@Controller
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Autowired
    public ProductController(ProductService productService, CategoryRepository categoryRepository, BrandRepository brandRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    @GetMapping
    public String  productList(Model model) {
        logger.info("product list");
        model.addAttribute("products", productService.findAll());

        return "products";
    }

    @GetMapping("new")
    public String createProduct(Model model) {
        logger.info("create list");
        model.addAttribute("product", new ProductRepr());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("brands", brandRepository.findAll());
        return "product";
    }

        @GetMapping("edit")
        public String editProduct(@RequestParam("id") Long id, Model model) {
            logger.info("Edit product with id " + id);

            model.addAttribute("product", productService.findById(id)
                    .orElseThrow(NotFoundException::new));
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("brands", brandRepository.findAll());
            return "product";
        }

        @DeleteMapping
        public String deleteProduct(@RequestParam("id") Long id) {
            logger.info("Delete product with id " + id);

            productService.deleteById(id);
            return "redirect:/product";
        }

    @PostMapping
    public String  saveProduct(@Valid ProductRepr product, BindingResult bindingResult) throws IOException {
        logger.info("save list");

        if (bindingResult.hasErrors()) {
            return "product";
        }

        if (product.getCost() == null) {
            bindingResult.rejectValue("cost", "", "Цена не должна быть пустой");
            return "product";
        }

        if (product.getCost().equals(BigDecimal.ZERO)) {
            bindingResult.rejectValue("cost", "", "Цена не должна быть 0");
            return "product";
        }

        productService.save(product);
        return "redirect:/product";
    }

}
