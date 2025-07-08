package ar.gob.pueblogeneralbelgrano.municipalidad.controller;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.permission.PermissionRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.permission.PermissionResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.response.ResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.service.permission.IPermissionService;
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
@RequestMapping("/permissions")
@PreAuthorize("denyAll()")
@Validated
public class PermissionController {
    private final IPermissionService permissionService;

    public PermissionController(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Operation(summary = "Obtener lista de permisos",
            description = "Devuelve la lista de permisos del sistema. Solo accesible por admins.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permisos retornados correctamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)"),
    })
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getPermissions() {
        List<PermissionResponseDTO> permissions = permissionService.getPermissions();

        ResponseDTO<List<PermissionResponseDTO>> getResponsePermissions = new ResponseDTO<>(permissions, 200, "Permisos retornados correctamente");

        return ResponseEntity.ok(getResponsePermissions);
    }

    @Operation(summary = "Obtener un permiso",
            description = "Obtener un permiso en particular. Accesible solamente para admins",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permiso retornado correctamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getPermissionById(@PathVariable Long id){

        PermissionResponseDTO permission = permissionService.getPermissionById(id);

        ResponseDTO<PermissionResponseDTO> getResponsePermission = new ResponseDTO<>(permission, 200, "Permission returned succesfully");

        return ResponseEntity.ok(getResponsePermission);
    }

    @Operation(summary = "Crear un permiso",
            description = "Retornar el permiso creado. Solo admins pueden crear nuevos permisos.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Permiso creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> savePermission(@Valid @RequestBody PermissionRequestDTO permission){
        permissionService.savePermission(permission);

        ResponseDTO<PermissionRequestDTO> savePermissionResponse = new ResponseDTO<>(permission, 200, "Permiso guardado con exito");

        return ResponseEntity.status(HttpStatus.CREATED).body(savePermissionResponse);
    }

    @Operation(summary = "Editar un permiso",
            description = "Retornar el permiso editado. Solo usuarios administradores pueden editar permisos.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permiso editado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updatePermission(@Valid @RequestBody PermissionRequestDTO permission, @PathVariable Long id){
        permissionService.updatePermission(permission, id);

        ResponseDTO<PermissionRequestDTO> updatePermissionResponse = new ResponseDTO<>(permission, 200, "Permiso editado correctamente");

        return ResponseEntity.status(HttpStatus.OK).body(updatePermissionResponse);
    }

    @Operation(summary = "Borrar un permiso",
            description = "Devuelve un mensaje de confirmacion. Solo administradores pueden borrar permisos.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permiso borrado correctamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePermission(@PathVariable Long id){
        permissionService.deletePermission(id);

        return ResponseEntity.status(HttpStatus.OK).body("Permiso eliminado");
    }
}
