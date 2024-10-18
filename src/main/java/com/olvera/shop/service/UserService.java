package com.olvera.shop.service;

import com.olvera.shop.dto.PageResponse;
import com.olvera.shop.dto.UserResponse;
import com.olvera.shop.exception.ResourceNotFoundException;
import com.olvera.shop.model.Role;
import com.olvera.shop.model.User;
import com.olvera.shop.repository.RoleRepository;
import com.olvera.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public UserResponse getUserById(String userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "userId", userId)
        );

        List<String> roles = user.getRole() != null ? List.of(user.getRole().getRoleName().name()) : List.of();

        return UserResponse.builder()
                .userId(userId)
                .username(user.getUsername())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .roles(roles)
                .accountNonLocked(user.isAccountNonLocked())
                .accountNonExpired(user.isAccountNonExpired())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                .credentialsExpiryDate(user.getCredentialsExpiryDate())
                .accountExpiryDate(user.getAccountExpiryDate())
                .isTwoFactorEnabled(user.isTwoFactorEnabled())
                .build();
    }

    public PageResponse getUserByEmail(String email, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo > 0 ? pageNo - 1 : 0, pageSize);

        Page<User> users = userRepository.findByEmail(email, pageable);

        List<UserResponse> userResponses = users.stream()
                .map(user -> {
                    Role role = roleRepository.findById(user.getRole().getRoleId())
                            .orElse(null);

                    return UserResponse.builder()
                            .userId(user.getUserId())
                            .username(user.getUsername())
                            .lastname(user.getLastname())
                            .firstname(user.getFirstname())
                            .email(user.getEmail())
                            .mobileNumber(user.getMobileNumber())
                            .accountNonLocked(user.isAccountNonLocked())
                            .accountNonExpired(user.isAccountNonExpired())
                            .credentialsNonExpired(user.isCredentialsNonExpired())
                            .enabled(user.isEnabled())
                            .credentialsExpiryDate(user.getCredentialsExpiryDate())
                            .accountExpiryDate(user.getAccountExpiryDate())
                            .isTwoFactorEnabled(user.isTwoFactorEnabled())
                            .roles(role != null
                                    ? Collections.singletonList(role.getRoleName().name())
                                    : Collections.emptyList())
                            .build();
                })
                .collect(Collectors.toList());

        return new PageResponse(
                userResponses,
                users.getNumber(),
                users.getSize(),
                users.getTotalElements(),
                users.getTotalPages(),
                users.isLast()
        );

    }

}
