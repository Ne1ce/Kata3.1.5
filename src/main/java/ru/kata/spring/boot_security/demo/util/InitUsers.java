package ru.kata.spring.boot_security.demo.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;



@Component
public class InitUsers {
    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public InitUsers(UserService userService,
                     RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }
    @Bean
    @Transactional
    public void addFirstUsers(){
        User user = new User("user",new BCryptPasswordEncoder().encode("user"),
                "Mr.User","Johns","user@mail.ru");
        User admin = new User("admin",new BCryptPasswordEncoder().encode("admin"),
                "Mr.Admin","Smith","admin@mail.ru");
        Role userRole =  roleRepository.save(new Role(1,"ROLE_USER"));
        Role adminRole = roleRepository.save(new Role(2,"ROLE_ADMIN"));
        user.addRole(userRole);
        admin.addRole(adminRole);
        admin.addRole(userRole);


        userService.addUser(user);
        userService.addUser(admin);


    }
}
