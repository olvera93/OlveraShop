package com.olvera.shop.repository;

import com.olvera.shop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

    Page<User> findByEmail(String email, Pageable pageable);

    Page<User> findByAccountNonLocked(boolean accountNonLocked, Pageable pageable);

    Page<User> findByFirstnameContainsAndLastnameContains(String firstname, String lastname, Pageable pageable);

}
