package com.karakoc.mezat.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    List<User> findAll();// BISMILLAHIRRAHMANIRRAHIM.

    Optional<User> findByUsername(String username);
    Optional<User> findByMail(String mail);

    Optional<User> findUserByToken(String token);

}
