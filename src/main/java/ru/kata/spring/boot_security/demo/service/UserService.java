package ru.kata.spring.boot_security.demo.service;

import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Collection;

public interface UserService extends UserDetailsService {
    Collection<User> allUsers();
    void addUser(User user);
    User showUser(Integer id);
    void deleteUser(Integer id);
    User findUserByUsername(String username);
    void updateUser(User user);
}
