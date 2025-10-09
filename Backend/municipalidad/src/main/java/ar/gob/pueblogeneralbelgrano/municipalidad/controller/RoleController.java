package ar.gob.pueblogeneralbelgrano.municipalidad.controller;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.response.ResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.role.RoleRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.role.RoleResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.service.permission.IPermissionService;
import ar.gob.pueblogeneralbelgrano.municipalidad.service.role.IRoleService;
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
import java.util.Map;

@RestController
@RequestMapping("/roles")
@PreAuthorize("denyAll()")
@Validated
public class RoleController {
    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Endpoint que Devuelve la lista de roles
     *
     * @return lista de roles
     */
    @Operation(summary = "Obtener roles",
            description = "Retorna los roles. Solo accedible por ADMINS.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles retornados con exito"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autorizado / No autenticado)"),
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO<List<RoleResponseDTO>>> getRoles() {
        List<RoleResponseDTO> roles = roleService.getRoles();

        ResponseDTO<List<RoleResponseDTO>> getResponseRoles = new ResponseDTO<>(roles, 200, "Roles retornados con exito");

        return ResponseEntity.ok(getResponseRoles);
    }

    /**
     * Endpoint que obtiene un rol especifico por ID. Solo accedible por admins.
     * @param id
     * @return rol por id
     */
    @Operation(summary = "Obtener un rol",
            description = "Retorna un rol. Solo accedible por admins",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol retornado con exito"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autorizado / No autenticado)"),
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO<RoleResponseDTO>> getRoleById(@PathVariable Long id){

        RoleResponseDTO role = roleService.getRoleById(id);

        ResponseDTO<RoleResponseDTO> getResponseRole = new ResponseDTO<>(role, 200, "Rol retornado con exito");

        return ResponseEntity.ok(getResponseRole);
    }

    /**
     * Endpoint que crea un nuevo rol en la base de datos. Solo accedible por admins
     * @param role
     * @return rol creado
     */
    @Operation(summary = "Crear un rol",
            description = "Retorna el rol creado. Solo accedible por admins",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rol creado con exito"),
            @ApiResponse(responseCode = "400", description = "Bad Request, error en validación de campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autorizado / No autenticado)"),
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO<RoleRequestDTO>> saveRole(@Valid @RequestBody RoleRequestDTO role){
        roleService.saveRole(role);

        ResponseDTO<RoleRequestDTO> saveRoleResponse = new ResponseDTO<>(role, 200, "Rol guardado con exito");

        return ResponseEntity.status(HttpStatus.CREATED).body(saveRoleResponse);
    }

    /**
     * Endpoint que edita un rol existente en la base de datos. Solo accedible por admins
     * @param role
     * @param id
     * @return rol editado
     */
    @Operation(summary = "Editar un rol",
            description = "Devuelve el rol editado. Solo accedible por admins",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role editado con exito"),
            @ApiResponse(responseCode = "400", description = "Bad Request, error en validacion de campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autorizado / No autenticado)"),
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO<RoleRequestDTO>> updateRole(@Valid @RequestBody RoleRequestDTO role, @PathVariable Long id){
        roleService.updateRole(role, id);

        ResponseDTO<RoleRequestDTO> updateRoleResponse = new ResponseDTO<>(role, 200, "Rol actualizado correctamente");

        return ResponseEntity.status(HttpStatus.OK).body(updateRoleResponse);
    }

    /**
     * Mensaje de confirmacion de eliminacion de rol. Solo accedible por admins
     * @param id
     * @return mensaje de confirmacion
     */
    @Operation(summary = "Borrar un rol",
            description = "Devuelve un mensaje de confirmacion. Solo accedible por admins.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol eliminado con exito"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autorizado / No autenticado)"),
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteRole(@PathVariable Long id){
        roleService.deleteRole(id);

        return ResponseEntity.ok(Map.of("message", "Rol eliminado con exito"));
    }
}
