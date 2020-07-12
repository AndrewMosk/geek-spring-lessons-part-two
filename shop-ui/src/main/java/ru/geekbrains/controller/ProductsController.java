package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.controller.repr.ProductRepr;
import ru.geekbrains.model.Category;
import ru.geekbrains.repo.BrandRepository;
import ru.geekbrains.repo.CategoryRepository;
import ru.geekbrains.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class ProductsController {

    private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);

    private final ProductService productService;

    private final CategoryRepository categoryRepository;
//
//    private final BrandRepository brandRepository;

    @Autowired
    public ProductsController(ProductService productService, CategoryRepository categoryRepository
                              ) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
//        this.brandRepository = brandRepository;
    }

    @GetMapping("/store")
    public String adminProductsPage(@RequestParam("category") String  cat_name, Model model) {
        model.addAttribute("activePage", "Products");
        List<ProductRepr> productReprs;

        if (cat_name.equals("All")) {
            productReprs = productService.findAll();
        } else {
            productReprs = productService.findByCatName(cat_name);
        }
        model.addAttribute("products", productReprs);

        List<Category> categories;
        if (cat_name.equals("All")) {
            categories = new ArrayList<>();
        } else {
            Category category = categoryRepository.findByName(cat_name);
            categories = categoryRepository.findCategoryWithParents(category.getId());
        }
        model.addAttribute("categories", categories);
        return "store";
    }

    @GetMapping("/product/{id}/view")
    public String adminEditProduct(Model model, @PathVariable("id") Long id) {
//        model.addAttribute("edit", true);
        model.addAttribute("activePage", "Products");
        model.addAttribute("product", productService.findById(id).orElseThrow(NotFoundException::new));

        Optional<ProductRepr> productReprOptional = productService.findById(id);
        if (productReprOptional.isPresent()) {
            ProductRepr productRepr = productReprOptional.get();
            model.addAttribute("categories", categoryRepository.findCategoryWithParents(productRepr.getCategory().getId()));
        }

//        model.addAttribute("categories", categoryRepository.findAll());
//        model.addAttribute("brands", brandRepository.findAll());
        return "product";
    }



}
