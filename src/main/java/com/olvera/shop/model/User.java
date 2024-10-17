package com.olvera.shop.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String userId;

    private String username;

    private String lastname;

    private String firstname;

    private String email;

    private String mobileNumber;

    private String password;

    private boolean accountNonLocked = true;

    private boolean accountNonExpired = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = true;

    private LocalDate credentialsExpiryDate;

    private LocalDate accountExpiryDate;

    private String twoFactorSecret;

    private boolean isTwoFactorEnabled = false;

    private String signUpMethod;

    @DocumentReference
    @ToString.Exclude
    private Role role;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    public User(String userName, String email, String password) {
        this.username = userName;
        this.email = email;
        this.password = password;
    }

    public User(String userName, String email) {
        this.username = userName;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof User)) return false;
        return userId != null && userId.equals(((User) o).getUserId());
    }

    @Override
    public int hashCode() {
        return getRole().hashCode();
    }

}




