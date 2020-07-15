package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.repr.ProductRepr;
import ru.geekbrains.model.Brand;
import ru.geekbrains.model.Category;
import ru.geekbrains.repo.BrandRepository;
import ru.geekbrains.repo.CategoryRepository;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.service.model.SelectedFilters;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
public class ProductsController {

    private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);

    private final ProductService productService;

    private final CategoryRepository categoryRepository;

    private final BrandRepository brandRepository;

    @Autowired
    public ProductsController(ProductService productService, CategoryRepository categoryRepository, BrandRepository brandRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

//    @GetMapping("/store")
//    public String adminProductsPage(@RequestParam(name = "category", required = false) String  cat_name,
//                                    @ModelAttribute(value="foo") Foo foo,
//                                    Model model) {
//
//        // как на мой взгляд должна работать фильтрация по производителям с помощью чекбоксов:
//        // каждый раз при нажатии на чек бокс из этого блока, нужно "проходить" по всем и присылать на сервер выбранные
//        // искать в бд нужные товары
//        // отправлять список выбранных производителей обратно, чтоб правильно расставить чекбоксы
//
////        List<String> checkedBrands = new ArrayList<>();
////        checkedBrands.add("Sony");
////        checkedBrands.add("DELL");
//
//
//
//        //List<Brand> brands;
//
//        Object object = model.getAttribute("brands");
//        List<ProductRepr> productReprs;
//        List<Category> categories;
//
//        if (cat_name == null) {
//            productReprs = productService.findAll();
//            categories = new ArrayList<>();
//        } else {
//            productReprs = productService.findByCatName(cat_name);
//
//            Category category = categoryRepository.findByName(cat_name);
//            categories = categoryRepository.findCategoryWithParents(category.getId());
//        }
//
//
//        model.addAttribute("products", productReprs);            // товары
//        model.addAttribute("categories", categories);            // иерархический список категорий (от младшей к старшей)
//        model.addAttribute("category", cat_name);                // младшая категория (та, которая выбрана)
//        model.addAttribute("brands", brandRepository.findAll()); // все производители (позже нужно сделать, чтоб отображались по категориям)
//        //model.addAttribute("checked_brands", checkedBrands);     // производители, выделенные для фильтрации
//
//        Map<Brand, String > brands;
//        brands = brandRepository.findAll().stream().collect(Collectors.toMap(br -> br, Brand::getName));
//
//        String confirm1 = "on";
//        model.addAttribute("confirm1", confirm1);
//        return "store";
//    }

    @GetMapping("/store")
    public String storePage(
                            @RequestParam(name = "category", required = false) String  cat_name,
                            Model model) {
        List<ProductRepr> productReprs;
        List<Category> categories;

        if (cat_name == null) {
            productReprs = productService.findAll();
            categories = new ArrayList<>();
            cat_name = "";
        } else {
            productReprs = productService.findByCatName(cat_name);

            Category category = categoryRepository.findByName(cat_name);
            categories = categoryRepository.findCategoryWithParents(category.getId());
        }

        model.addAttribute("products", productReprs);            // товары
        model.addAttribute("categories", categories);            // иерархический список категорий (от младшей к старшей)
        model.addAttribute("category", cat_name);                // младшая категория (та, которая выбрана)
        model.addAttribute("selectableBrands", brandRepository.findAll()); // все производители (позже нужно сделать, чтоб отображались по категориям)

        SelectedFilters selectedFilters = new SelectedFilters(1);
        selectedFilters.setBrands(new ArrayList<>());
        selectedFilters.setCategory(cat_name);

        model.addAttribute("selectedFilters", selectedFilters);
        return "store";
    }

    @PostMapping("/store")
    public String postBrands(@ModelAttribute("selectedFilters") SelectedFilters selectedFilters,
                              Model model) {

        List<ProductRepr> productReprs;
        List<Category> categories;


        List<String> brandsFilter = selectedFilters.getBrands().stream().map(Brand::getName).collect(Collectors.toList());
        String cat_name = selectedFilters.getCategory();

        if (brandsFilter.isEmpty() && cat_name.isEmpty()) {
            productReprs = productService.findAll();
            categories = new ArrayList<>();
        } else if (!brandsFilter.isEmpty() && !cat_name.isEmpty()) {
            productReprs = productService.findByCatNameAndBrands(cat_name, brandsFilter);

            Category category = categoryRepository.findByName(cat_name);
            categories = categoryRepository.findCategoryWithParents(category.getId());
        } else if (!cat_name.isEmpty()) {
            productReprs = productService.findByCatName(cat_name);

            Category category = categoryRepository.findByName(cat_name);
            categories = categoryRepository.findCategoryWithParents(category.getId());
        } else {
            productReprs = productService.findProductByBrand(selectedFilters.getBrands());
            categories = new ArrayList<>();
        }


        model.addAttribute("products", productReprs);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedFilters", selectedFilters);
        model.addAttribute("selectableBrands", brandRepository.findAll());
        return "store";
    }

    @GetMapping("/product/{id}/view")
    public String adminEditProduct(Model model, @PathVariable("id") Long id) {
        model.addAttribute("activePage", "Products");
        model.addAttribute("product", productService.findById(id).orElseThrow(NotFoundException::new));

        Optional<ProductRepr> productReprOptional = productService.findById(id);
        if (productReprOptional.isPresent()) {
            ProductRepr productRepr = productReprOptional.get();
            model.addAttribute("categories", categoryRepository.findCategoryWithParents(productRepr.getCategory().getId()));
        }

        return "product";
    }
}