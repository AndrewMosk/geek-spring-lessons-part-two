package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.geekbrains.model.Role;
import ru.geekbrains.repo.RoleRepository;
import ru.geekbrains.service.NotFoundException;

import javax.validation.Valid;

@RequestMapping("/role")
@Controller
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    private final RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String roleList(Model model) {

        logger.info("Role list");
        model.addAttribute("roles", roleRepository.findAll());

        return "roles";
    }

    @GetMapping("new")
    public String createRole(Model model) {
        logger.info("Create role form");

        model.addAttribute("role", new Role());
        return "role";
    }

    @GetMapping("edit")
    public String editRole(@RequestParam("id") Long id, Model model) {
        logger.info("Edit role with id " + id);

        model.addAttribute("role", roleRepository.findById(id)
                .orElseThrow(NotFoundException::new));
        return "role";
    }

    @PostMapping
    public String saveRole(@Valid Role role) {
        logger.info("Save role method");

        roleRepository.save(role);
        return "redirect:/role";
    }

    @DeleteMapping
    public String deleteRole(@RequestParam("id") Long id) {
        logger.info("Delete role with id " + id);

        roleRepository.deleteById(id);
        return "redirect:/role";
    }

}
