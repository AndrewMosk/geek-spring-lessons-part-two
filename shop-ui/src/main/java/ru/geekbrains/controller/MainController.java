package ru.geekbrains.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("activePage", "None");
        return "index";
    }

    @RequestMapping("/blank")
    public String blank(Model model) {
        model.addAttribute("activePage", "None");
        return "blank";
    }

    @RequestMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("activePage", "None");
        return "checkout";
    }

    @RequestMapping("/product")
    public String product(Model model) {
        model.addAttribute("activePage", "None");
        return "product";
    }

    @RequestMapping("/store")
    public String store(Model model) {
        model.addAttribute("activePage", "None");
        return "store";
    }
}
