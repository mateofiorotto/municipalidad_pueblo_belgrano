package ar.gob.pueblogeneralbelgrano.municipalidad.controller;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.response.ResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecUpdateDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.service.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@PreAuthorize("denyAll()")
@Validated
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get users",
            description = "Return the list of users. Only users with ADMIN role.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users returned succesfully"),
            @ApiResponse(responseCode = "403", description = "Access Denied"),
            @ApiResponse(responseCode = "500", description = "Invalid Token. (Unauthorized/Not Authenticated)"),
    })
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUsers() {
        List<UserSecResponseDTO> users = userService.getUsers();

        ResponseDTO<List<UserSecResponseDTO>> getResponseUsers = new ResponseDTO<>(users, 200, "Users returned successfully");

        return ResponseEntity.ok(getResponseUsers);
    }

    @Operation(summary = "Get user",
            description = "Return one user. Only users with ADMIN role.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User returned succesfully"),
            @ApiResponse(responseCode = "403", description = "Access Denied"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Invalid Token. (Unauthorized/Not Authenticated)")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long id){

        UserSecResponseDTO user = userService.getUserById(id);

        ResponseDTO<UserSecResponseDTO> getResponseUser = new ResponseDTO<>(user, 200, "User returned succesfully");

        return ResponseEntity.ok(getResponseUser);
    }

    @Operation(summary = "Create a user",
            description = "Return the created user. Only the users with ADMIN role can create new users.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created succesfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request, fields validation error"),
            @ApiResponse(responseCode = "403", description = "Access Denied"),
            @ApiResponse(responseCode = "500", description = "Invalid Token. (Unauthorized/Not Authenticated)")
    })
    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserSecRequestDTO user){
        userService.saveUser(user);

        ResponseDTO<UserSecRequestDTO> saveUserResponse = new ResponseDTO<>(user, 200, "User saved succesfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(saveUserResponse);
    }

    @Operation(summary = "Edit a user",
            description = "Return the edited user. Only the users with ADMIN role can edit users.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User edited succesfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request, fields validation error"),
            @ApiResponse(responseCode = "403", description = "Access Denied"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Invalid Token. (Unauthorized/Not Authenticated)")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserSecUpdateDTO user, @PathVariable Long id){
        userService.updateUser(user, id);

        ResponseDTO<UserSecUpdateDTO> updateUserResponse = new ResponseDTO<>(user, 200, "User updated succesfully");

        return ResponseEntity.status(HttpStatus.OK).body(updateUserResponse);
    }

    @Operation(summary = "Delete a user",
            description = "Return a confirmation message. Only the users with ADMIN role can delete users.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted succesfully"),
            @ApiResponse(responseCode = "403", description = "Access Denied"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Invalid Token. (Unauthorized/Not Authenticated)")
    })
    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.OK).body("User deleted");
    }
}
