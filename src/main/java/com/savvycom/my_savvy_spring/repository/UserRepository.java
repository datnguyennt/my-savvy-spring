package com.savvycom.my_savvy_spring.repository;

import com.savvycom.my_savvy_spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByUserName(String username);

    Optional<User> findUserByEmail(String email);

}
