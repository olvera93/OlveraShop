package com.olvera.shop.controller;

import com.olvera.shop.dto.ErrorResponse;
import com.olvera.shop.dto.PageResponse;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/admin", produces = (MediaType.APPLICATION_JSON_VALUE))
@CrossOrigin("*")
@Tag(name = "Admin", description = "Admins API")
public class AdminController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Get user's by parameters",
            description = "You can get all users by parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found User", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error with the server", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<PageResponse> getUserByParameters(
            @Parameter(name = "filterBy", description = "Name of the fields to retrieve (values are: email, name, mobileNumber, accountNonLocked)", example = "email", required = true)
            @RequestParam(value = "filterBy", defaultValue = "email") String by,
            @Parameter(name = "value", description = "value that you want to search", example = "paco2@gmail.com", required = true)
            @RequestParam(value = "value", defaultValue = "paco2@gmail.com") String value,
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        PageResponse result = switch (by) {
            case "name" -> {
                String[] names = value.split(" ");
                if (names.length != 2) {
                    throw new RuntimeException("Invalid format for 'value'. It must contain firstname and lastname separated by space.");
                }
                String firstname = names[0];
                String lastname = names[1];
                yield userService.getUserByFirstnameAndLastname(firstname, lastname, pageNo, pageSize);
            }
            case "email" -> userService.getUserByEmail(value, pageNo, pageSize);
            case "accountNonLocked" -> {
                boolean isAccountNonLocked = Boolean.parseBoolean(value);

                yield userService.getUserByAccountNonLocked(isAccountNonLocked, pageNo, pageSize);
            }
            default -> throw new ResourceNotFoundException("By", "by", by);
        };

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }


}
