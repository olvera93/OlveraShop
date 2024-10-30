package com.olvera.shop.service;

import com.olvera.shop.dto.PageResponse;
import com.olvera.shop.exception.ResourceNotFoundException;
import com.olvera.shop.model.User;
import com.olvera.shop.repository.RoleRepository;
import com.olvera.shop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest extends AbstractServiceTest {


    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Test
    void testGetUserByFirstnameAndLastname_UserExist() {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> users = new PageImpl<>(Collections.singletonList(user), pageable, 1);

        when(userRepository.findByFirstnameContainsAndLastnameContains(user.getFirstname(), user.getLastname(), pageable)).thenReturn(users);

        PageResponse result = userService.getUserByFirstnameAndLastname(user.getFirstname(), user.getLastname(), pageNo, pageSize);

        userResponse = result.getUsers().get(0);

        assertNotNull(result);
        assertEquals(user.getUserId(), userResponse.getUserId());
        assertEquals(user.getFirstname(), userResponse.getFirstname());
        assertEquals(user.getLastname(), userResponse.getLastname());
        assertEquals(user.getUsername(), userResponse.getUsername());
        assertEquals(user.getEmail(), userResponse.getEmail());
    }

    @Test
    void testGetUserByFirstnameAndLastname_UserNoExist() {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        when(userRepository.findByFirstnameContainsAndLastnameContains("Paco", "Sanchez Mendez", pageable))
                .thenReturn(Page.empty(pageable));

        assertThatThrownBy(() -> {
            userService.getUserByFirstnameAndLastname("Paco", "Sanchez Mendez", pageNo, pageSize);
        }).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found with the given input data Firstname and Lastname: 'Paco Sanchez Mendez'");
    }

    @Test
    void testGetUserByFirstnameAndLastname_FirstnameIsEmpty(){
        assertThatThrownBy(() -> {
            userService.getUserByFirstnameAndLastname("", "Sanchez Mendez", pageNo, pageSize);
        }).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Can't be empty or null");
    }

    @Test
    void testGetUserByFirstnameAndLastname_LastnameIsEmpty(){
        assertThatThrownBy(() -> {
            userService.getUserByFirstnameAndLastname("Paco", "", pageNo, pageSize);
        }).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Can't be empty or null");
    }

    @Test
    void testGetUserByEmail_UserHasEmail() {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> userPage = new PageImpl<>(Collections.singletonList(user), pageable, 1);

        when(userRepository.findByEmail(email, pageable)).thenReturn(userPage);
        when(roleRepository.findById(user.getRole().getRoleId())).thenReturn(Optional.of(role));

        PageResponse result = userService.getUserByEmail(email, pageNo, pageSize);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertTrue(result.isLast());
        assertEquals(1, result.getUsers().size());

        userResponse = result.getUsers().get(0);

        assertEquals(user.getUserId(), userResponse.getUserId());
        assertEquals(user.getEmail(), userResponse.getEmail());
        assertEquals(user.getUsername(), userResponse.getUsername());
        assertEquals(user.getFirstname(), userResponse.getFirstname());
        assertEquals(user.getLastname(), userResponse.getLastname());
        assertEquals(user.getMobileNumber(), userResponse.getMobileNumber());
        assertEquals(Collections.singletonList(user.getRole().getRoleName().name()), userResponse.getRoles());

    }

    @Test
    void testGetUserByEmail_UserHasNoEmail() {

        String email = "test@example.com";

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(userRepository.findByEmail(email, pageable)).thenReturn(emptyPage);

        PageResponse result = userService.getUserByEmail(email, pageNo, pageSize);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.isLast());
        assertTrue(result.getUsers().isEmpty());

    }

    @Test
    void getUserByUserId() {

        // Fail case
        when(userRepository.findById(any(String.class))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> {
            userService.getUserById("nuinfds8");
        }).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found with the given input data userId: 'nuinfds8'");

        // Success case
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userResponse = userService.getUserById(userId);
        assertNotNull(userResponse);
        assertEquals("ivmorv80", userResponse.getUserId());
        assertEquals("Fernandez Sanchez", userResponse.getLastname());
        assertEquals("Victor", userResponse.getFirstname());
        assertEquals("Victor23", userResponse.getUsername());
        assertEquals("victor2@gmail.com", userResponse.getEmail());
        assertEquals("5599330022", userResponse.getMobileNumber());
    }

}
