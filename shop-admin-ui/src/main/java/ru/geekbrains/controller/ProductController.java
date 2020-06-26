package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.model.Product;
import ru.geekbrains.service.ProductService;


import javax.validation.Valid;
import java.math.BigDecimal;

@RequestMapping("/product")
@Controller
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String  productList(Model model,
                               @RequestParam(name = "minPrice",required = false, defaultValue = "false") Boolean minPrice,
                               @RequestParam(name = "maxPrice",required = false, defaultValue = "false") Boolean maxPrice,
                               @RequestParam(name = "filterName",required = false, defaultValue = "") String filterName,
                               @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                               @RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
        logger.info("product list");

        Page<Product> productPage = productService.filterByParams(filterName, minPrice, maxPrice, PageRequest.of(page-1, size));

        model.addAttribute("productsPage", productPage);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("filterName", filterName);
        model.addAttribute("prevPageNumber", productPage.hasPrevious() ? productPage.previousPageable().getPageNumber() + 1 : -1);
        model.addAttribute("nextPageNumber", productPage.hasNext() ? productPage.nextPageable().getPageNumber() + 1 : -1);
        return "products";
    }

    @GetMapping("new")
    public String createProduct(Model model) {
        logger.info("create list");
        model.addAttribute("product", new Product());
        return "product";
    }

//    @GetMapping("edit/{id}")
//    public String editProduct(@PathVariable("id") Integer productId, Model model) {
//        logger.info("edit item");
//        model.addAttribute("product", productService.findById(productId));
//
//        return "product";
//    }
        @GetMapping("edit")
        public String editProduct(@RequestParam("id") Long id, Model model) {
            logger.info("Edit user with id " + id);

            model.addAttribute("product", productService.findById(id));
            return "product";
        }

//    @GetMapping("delete/{id}")
//    public String deleteProduct(@PathVariable("id") Integer productId, Model model) {
//        logger.info("delete item");
//        productService.deleteById(productId);
//
//        return "redirect:/product";
//    }
        @DeleteMapping
        public String deleteProduct(@RequestParam("id") Long id) {
            logger.info("Delete user with id " + id);

            productService.deleteById(id);
            return "redirect:/product";
        }

    @PostMapping
    public String  saveProduct(@Valid Product product, BindingResult bindingResult) {
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
