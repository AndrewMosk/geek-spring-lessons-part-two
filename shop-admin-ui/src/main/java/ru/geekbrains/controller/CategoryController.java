package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.geekbrains.model.Category;
import ru.geekbrains.repo.CategoryRepository;
import ru.geekbrains.service.NotFoundException;

import javax.validation.Valid;

@RequestMapping("/category")
@Controller
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String categoryList(Model model) {

        logger.info("Category list");
        model.addAttribute("categories", categoryRepository.findAll());

        return "categories";
    }

    @GetMapping("new")
    public String createCategory(Model model) {
        logger.info("Create category form");

        model.addAttribute("category", new Category());
        return "category";
    }

    @GetMapping("edit")
    public String editCategory(@RequestParam("id") Long id, Model model) {
        logger.info("Edit category with id " + id);

        model.addAttribute("category", categoryRepository.findById(id)
                .orElseThrow(NotFoundException::new));
        return "category";
    }

    @PostMapping
    public String saveCategory(@Valid Category category) {
        logger.info("Save category method");

        categoryRepository.save(category);
        return "redirect:/category";
    }

    @DeleteMapping
    public String deleteCategory(@RequestParam("id") Long id) {
        logger.info("Delete category with id " + id);

        categoryRepository.deleteById(id);
        return "redirect:/category";
    }

}
