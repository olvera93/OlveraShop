package com.olvera.shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private String userId;

    @NotEmpty(message = "Username can not be a null or empty")
    @Schema(name = "username", description = "The user's nickname", example = "Paco234")
    private String username;

    @NotEmpty(message = "LastName can not be a null or empty")
    @Size(min = 3, max = 30, message = "The length of the lastname should be between 5 and 30")
    @Schema(name = "lastname", description = "The user's lastname", example = "Sanchez Perez")
    private String lastname;

    @NotEmpty(message = "Firstname can not be a null or empty")
    @Size(min = 3, max = 30, message = "The length of the firstname should be between 2 and 30")
    @Schema(name = "firstname", description = "The user's firstname", example = "Paco")
    private String firstname;

    @NotEmpty(message = "Email address can not be a null or empty")
    @Email(message = "Email address should be a valid value")
    @Schema(name = "email", description = "The user's email", example = "paco2@gmail.com")
    private String email;

    @Pattern(regexp = "($|[0-9]{10})", message = "Mobile number must be 10 digits")
    @Schema(name = "mobileNumber", description = "The user's mobileNumber", example = "5599443399")
    private String mobileNumber;

    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private LocalDate credentialsExpiryDate;
    private LocalDate accountExpiryDate;
    private boolean isTwoFactorEnabled;
    private List<String> roles;
}
