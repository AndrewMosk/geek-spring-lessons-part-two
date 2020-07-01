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

import ru.geekbrains.model.User;
import ru.geekbrains.repo.RoleRepository;
import ru.geekbrains.service.NotFoundException;
import ru.geekbrains.service.UserService;

import javax.validation.Valid;

@RequestMapping("/user")
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String userList(Model model) {

        logger.info("User list");
        model.addAttribute("users", userService.findAll());

        return "users";
    }

    @GetMapping("new")
    public String createUser(Model model) {
        logger.info("Create user form");

        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "user";
    }

    @GetMapping("edit")
    public String editUser(@RequestParam("id") Long id, Model model) {
        logger.info("Edit user with id " + id);

        model.addAttribute("user", userService.findById(id)
                .orElseThrow(NotFoundException::new));
        model.addAttribute("roles", roleRepository.findAll());
        return "user";
    }

    @PostMapping
    public String saveUser(@Valid User user, BindingResult bindingResult) {
        logger.info("Save user method");

        if (bindingResult.hasErrors()) {
            return "user";
        }
        if (!user.getPassword().equals(user.getRepeatPassword())) {
            bindingResult.rejectValue("repeatPassword", "", "Пароли не совпадают");
            return "user";
        }

        userService.save(user);
        return "redirect:/user";
    }

    @DeleteMapping
    public String deleteUser(@RequestParam("id") Long id) {
        logger.info("Delete user with id " + id);

        userService.delete(id);
        return "redirect:/user";
    }

}
