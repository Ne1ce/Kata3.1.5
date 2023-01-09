package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String allUsers(Model model, Principal principal) {

        model.addAttribute("users", userService.findByUsername(principal.getName()));
        return "userPage";
    }


    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Integer id, Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (id != user.getId()) {
            return "redirect:/user/show/" + user.getId();
        }
        model.addAttribute("user", userService.showUser(id));
        return "user";
    }
}
