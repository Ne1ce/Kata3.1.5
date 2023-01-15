package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> allUsers();
    void addUser(User user);
    User showUser(Integer id);
    void deleteUser(Integer id);
    User findUserByEmail(String email);
}
