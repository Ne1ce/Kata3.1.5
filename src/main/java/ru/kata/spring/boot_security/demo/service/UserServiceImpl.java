package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void addUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        System.out.println(user);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User showUser(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Юзер '%s' не найден", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getAuthorities());
    }

}
