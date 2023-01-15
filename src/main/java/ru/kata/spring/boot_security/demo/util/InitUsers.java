package ru.kata.spring.boot_security.demo.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;



@Component
public class InitUsers {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public InitUsers(UserService userService,
                     RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @Bean
    @Transactional
    public void addFirstUsers(){
        User user = new User("Mr.User","Johns",
                "user@mail.ru",23,"user");
        User admin = new User("Mr.Admin","Smith",
                "admin@mail.ru",30,"admin");
        user.addRole(roleService.addRole(new Role(1,"ROLE_USER")));
        admin.addRole(roleService.addRole(new Role(2,"ROLE_ADMIN")));
        admin.addRole(roleService.addRole(new Role(1,"ROLE_USER")));


        userService.addUser(user);
        userService.addUser(admin);


    }
}
