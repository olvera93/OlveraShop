package com.olvera.shop.controller;

import com.olvera.shop.dto.ErrorResponse;
import com.olvera.shop.dto.PageResponse;
import com.olvera.shop.dto.UserResponse;
import com.olvera.shop.exception.ResourceNotFoundException;
import com.olvera.shop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1", produces = (MediaType.APPLICATION_JSON_VALUE))
@CrossOrigin("*")
@Tag(name = "User", description = "Users API")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Get a user by id",
            description = "You can get a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found product", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable(name = "userId")
            String userId
    ) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

}
