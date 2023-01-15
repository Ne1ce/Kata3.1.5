package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;

    }

    @GetMapping()
    public String allUsers(ModelMap model, Principal principal) {
        model.addAttribute("authUser", userService.findUserByEmail(principal.getName()));
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("create", new User());
        model.addAttribute("roles", roleService.allRoles());
        return "admin";
    }


    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.allRoles());
        return "admin";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        return "admin";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @ModelAttribute("user") User user /*@PathVariable("id") Integer id*/) {
//        model.addAttribute("user", userService.showUser(id));
        model.addAttribute("roles", roleService.allRoles());
        return "admin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }
}
