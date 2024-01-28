package hexlet.code.controller;

import hexlet.code.dto.UserDTO;
import hexlet.code.exception.AccessDeniedException;
import hexlet.code.service.UserService;
import hexlet.code.util.UserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;



import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {


    private final UserService userService;

    private final UserUtils userUtils;

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Get list of all users")
    @ApiResponse(responseCode = "200", description = "List of all users")
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDTO>> index() {
        List<UserDTO> usersDto = userService.getAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(usersDto.size()))
                .body(usersDto);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Get a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User with that id not found")
    })
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO show(
            @Parameter(description = "Id of user to be found")
            @PathVariable Long id) {
        return userService.getById(id);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Create new user")
    @ApiResponse(responseCode = "201", description = "User created")
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(
            @Parameter(description = "User data to save")
            @Valid @RequestBody UserDTO data) {
        return userService.create(data);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Update user by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "404", description = "User with that id not found")
    })
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO update(
            @Parameter(description = "Id of user to be updated")
            @PathVariable Long id,
            @Parameter(description = "User data to update")
            @Valid @RequestBody UserDTO data) {
        var user = userUtils.getCurrentUser();

        if (Objects.equals(user.getId(), id)) {
            return userService.update(id, data);
        } else {
            throw new AccessDeniedException("You do not have the rights to perform this operation");
        }
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Delete user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User with that id hot found")
    })
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "Id of user to be deleted")
            @PathVariable Long id) {
        var user = userUtils.getCurrentUser();

        if (Objects.equals(user.getId(), id)) {
            userService.delete(id);
        } else {
            throw new AccessDeniedException("You do not have the rights to perform this operation");
        }
    }
}
