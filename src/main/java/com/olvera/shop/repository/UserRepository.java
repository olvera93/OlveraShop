package com.olvera.shop.repository;

import com.olvera.shop.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    Optional<User> findByLastname(String lastname);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);
}
