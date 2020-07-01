package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.geekbrains.model.Brand;
import ru.geekbrains.repo.BrandRepository;
import ru.geekbrains.service.NotFoundException;

import javax.validation.Valid;

@RequestMapping("/brand")
@Controller
public class BrandController {

    private static final Logger logger = LoggerFactory.getLogger(BrandController.class);

    private final BrandRepository brandRepository;

    @Autowired
    public BrandController(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @GetMapping
    public String brandList(Model model) {

        logger.info("Brand list");
        model.addAttribute("brands", brandRepository.findAll());

        return "brands";
    }

    @GetMapping("new")
    public String createBrand(Model model) {
        logger.info("Create brand form");

        model.addAttribute("brand", new Brand());
        return "brand";
    }

    @GetMapping("edit")
    public String editBrand(@RequestParam("id") Long id, Model model) {
        logger.info("Edit brand with id " + id);

        model.addAttribute("brand", brandRepository.findById(id)
                .orElseThrow(NotFoundException::new));
        return "brand";
    }

    @PostMapping
    public String saveBrand(@Valid Brand brand) {
        logger.info("Save brand method");

        brandRepository.save(brand);
        return "redirect:/brand";
    }

    @DeleteMapping
    public String deleteBrand(@RequestParam("id") Long id) {
        logger.info("Delete brand with id " + id);

        brandRepository.deleteById(id);
        return "redirect:/brand";
    }

}
