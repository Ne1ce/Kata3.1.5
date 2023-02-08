package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


import java.util.Collection;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    //encoder через внедрение теперь пункт 11
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }


    @Override
    public Collection<User> allUsers() {

        return userRepository.allUsers();
    }

    @Transactional
    @Override
    public void addUser(User user) {
        User newUser = findUserByUsername(user.getUsername());
        if(newUser == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }
    @Transactional
    @Override
    public void updateUser(User user){
        User userToUpdate = findUserByUsername(user.getUsername());
        if(userToUpdate != null) {
         //   добавлена проверка на пустой пароль пункт 1.
            if(user.getPassword().equals("")) {
                user.setPassword(userToUpdate.getPassword());
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userRepository.save(user);
        }
    }


    @Override
    public User showUser(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    //все readonly транзакции удалены
    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }


    @Override
//    @Transactional(readOnly = true) //удалено пункт 12
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Юзер '%s' не найден", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getAuthorities());
    }

}
