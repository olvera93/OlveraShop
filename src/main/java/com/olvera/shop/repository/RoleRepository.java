package com.olvera.shop.repository;

import com.olvera.shop.model.AppRole;
import com.olvera.shop.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByRoleName(AppRole appRole);

}
