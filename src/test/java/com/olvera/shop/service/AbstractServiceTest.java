package com.olvera.shop.service;

import com.olvera.shop.dto.UserResponse;
import com.olvera.shop.model.AppRole;
import com.olvera.shop.model.Role;
import com.olvera.shop.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AbstractServiceTest {

    protected User user;

    protected Role role;

    protected String roleId;

    protected String userId;

    protected String email;

    protected Integer pageNo;

    protected Integer pageSize;

    protected UserResponse userResponse;

    @BeforeEach
    public void prepare() {
        userId = "ivmorv80";

        email = "victor2@gmail.com";

        roleId = "ISOJDAS9";

        pageNo = 0;

        pageSize = 10;

        role = Role.builder()
                .roleId(roleId)
                .roleName(AppRole.ROLE_USER)
                .build();

        userResponse = UserResponse.builder()
                .userId(userId)
                .lastname("Fernandez Sanchez")
                .firstname("Victor")
                .username("Victor23")
                .email("victor2@gmail.com")
                .mobileNumber("5599330022")
                .build();

        user = User.builder()
                .userId(userId)
                .lastname("Fernandez Sanchez")
                .firstname("Victor")
                .username("Victor23")
                .email("victor2@gmail.com")
                .mobileNumber("5599330022")
                .role(role)
                .build();

    }



}
