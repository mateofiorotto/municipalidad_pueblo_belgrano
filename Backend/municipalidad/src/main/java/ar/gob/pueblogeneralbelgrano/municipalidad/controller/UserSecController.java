package ar.gob.pueblogeneralbelgrano.municipalidad.controller;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.response.ResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.service.usersec.IUserSecService;
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
public class UserSecController {
    private final IUserSecService userService;

    public UserSecController(IUserSecService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint que obtiene la lista completa de usuarios. Solo accedible por admins.
     *
     * @return lista de usuarios
     */
    @Operation(summary = "Obtener usuarios",
            description = "Retornar lista de usuarios. Solo accedible por admins.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios retornados con exito"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autorizado / No autenticado)"),
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO<List<UserSecResponseDTO>>> getUsers() {
        List<UserSecResponseDTO> users = userService.getUsers();

        ResponseDTO<List<UserSecResponseDTO>> getResponseUsers = new ResponseDTO<>(users, 200, "Usuarios retornados con exito");

        return ResponseEntity.ok(getResponseUsers);
    }

    /**
     * Endpoint que Obtiene un usuario especifico por su ID Solo accedible por admins.
     *
     * @param id
     * @return un usuario
     */
    @Operation(summary = "Obtener un usuario",
            description = "Obtener un usuario por ID. Solo accesible por admins.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario retornado con exito"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autorizado / No autenticado)"),
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO<UserSecResponseDTO>> getUserById(@PathVariable Long id){

        UserSecResponseDTO user = userService.getUserById(id);

        ResponseDTO<UserSecResponseDTO> getResponseUser = new ResponseDTO<>(user, 200, "Usuario retornado con exito");

        return ResponseEntity.ok(getResponseUser);
    }

    /**
     * Endpoint que Guarda un usuario en la base de datos Solo accedible por admins.
     *
     * @param user
     * @return usuario guardado
     */
    @Operation(summary = "Crear usuario",
            description = "Devuelve el usuario creado. Solo accedible por admins.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado con exito"),
            @ApiResponse(responseCode = "400", description = "Bad request, error en validacion de campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autorizado / No autenticado)"),
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO<UserSecRequestDTO>> saveUser(@Valid @RequestBody UserSecRequestDTO user){
        userService.saveUser(user);

        ResponseDTO<UserSecRequestDTO> saveUserResponse = new ResponseDTO<>(user, 200, "Usuario guardado correctamente");

        return ResponseEntity.status(HttpStatus.CREATED).body(saveUserResponse);
    }

    /**
     * Endpoint que Edita un usuario de la base de datos Solo accedible por admins.
     *
     * @param user
     * @param id
     * @return usuario editado
     */
    @Operation(summary = "Editar un usuario",
            description = "Devuelve el usuario editado. Solo accesible por administradores.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario editado con exito"),
            @ApiResponse(responseCode = "400", description = "Bad Request, error en la validación de campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autorizado / No autenticado)"),
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO<UserSecRequestDTO>> updateUser(@Valid @RequestBody UserSecRequestDTO user, @PathVariable Long id){
        userService.updateUser(user, id);

        ResponseDTO<UserSecRequestDTO> updateUserResponse = new ResponseDTO<>(user, 200, "Usuario actualizado con exito");

        return ResponseEntity.status(HttpStatus.OK).body(updateUserResponse);
    }

    /**
     * Endpoint que Elimina un usuario desde la base de datos Solo accedible por admins.
     *
     * @param id
     * @return mensaje de confirmacion
     */
    @Operation(summary = "Eliminar un usuario",
            description = "Devuelve un mensaje de confirmacion. Solo accedible por admins",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado con exito"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autorizado / No autenticado)"),
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado");
    }
}
