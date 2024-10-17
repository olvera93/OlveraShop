package com.olvera.shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "Username can not be a null or empty")
    @Schema(name = "username", description = "The user's nickname", example = "Paco2782")
    private String username;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$",
            message = "Password is 8-16 characters with at least one digit, one uppercase letter, one lowercase letter, and one special character")
    @Schema(name = "password", description = "The user's password", example = "Pedro1232!")
    private String password;
}
