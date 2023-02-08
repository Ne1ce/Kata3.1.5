package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Collection;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @EntityGraph(value = "user.roles",type = EntityGraph.EntityGraphType.LOAD)
    User findUserByUsername(String username);

    @EntityGraph(value = "user.roles",type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = "select u from User u")
    Collection<User> allUsers();

}
