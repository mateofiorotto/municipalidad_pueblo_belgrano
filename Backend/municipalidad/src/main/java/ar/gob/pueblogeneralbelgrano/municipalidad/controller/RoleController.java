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

@RestController
@RequestMapping("/roles")
@PreAuthorize("denyAll()")
@Validated
public class RoleController {
    private final IRoleService roleService;
    private final IPermissionService permissionService;

    public RoleController(IRoleService roleService, IPermissionService permissionService) {
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    @Operation(summary = "Get roles",
            description = "Return the list of roles. Only users with ADMIN, TEACHER or STUDENT roles.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles returned succesfully"),
            @ApiResponse(responseCode = "403", description = "Access Denied"),
            @ApiResponse(responseCode = "500", description = "Invalid Token. (Unauthorized/Not Authenticated)"),
    })
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getRoles() {
        List<RoleResponseDTO> roles = roleService.getRoles();

        ResponseDTO<List<RoleResponseDTO>> getResponseRoles = new ResponseDTO<>(roles, 200, "Roles returned successfully");

        return ResponseEntity.ok(getResponseRoles);
    }

    @Operation(summary = "Get role",
            description = "Return one role. Only users with ADMIN, TEACHER or STUDENT roles.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role returned succesfully"),
            @ApiResponse(responseCode = "403", description = "Access Denied"),
            @ApiResponse(responseCode = "404", description = "Role not found"),
            @ApiResponse(responseCode = "500", description = "Invalid Token. (Unauthorized/Not Authenticated)")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getRoleById(@PathVariable Long id){

        RoleResponseDTO role = roleService.getRoleById(id);

        ResponseDTO<RoleResponseDTO> getResponseRole = new ResponseDTO<>(role, 200, "Role returned succesfully");

        return ResponseEntity.ok(getResponseRole);
    }

    @Operation(summary = "Create a role",
            description = "Return the created role. Only the users with ADMIN role can create new roles.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role created succesfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request, fields validation error"),
            @ApiResponse(responseCode = "403", description = "Access Denied"),
            @ApiResponse(responseCode = "500", description = "Invalid Token. (Unauthorized/Not Authenticated)")
    })
    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveRole(@Valid @RequestBody RoleRequestDTO role){
        roleService.saveRole(role);

        ResponseDTO<RoleRequestDTO> saveRoleResponse = new ResponseDTO<>(role, 200, "Role saved succesfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(saveRoleResponse);
    }

    @Operation(summary = "Edit a role",
            description = "Return the edited role. Only the users with ADMIN role can edit roles.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role edited succesfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request, fields validation error"),
            @ApiResponse(responseCode = "403", description = "Access Denied"),
            @ApiResponse(responseCode = "404", description = "Role not found"),
            @ApiResponse(responseCode = "500", description = "Invalid Token. (Unauthorized/Not Authenticated)")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRole(@Valid @RequestBody RoleRequestDTO role, @PathVariable Long id){
        roleService.updateRole(role, id);

        ResponseDTO<RoleRequestDTO> updateRoleResponse = new ResponseDTO<>(role, 200, "Role updated succesfully");

        return ResponseEntity.status(HttpStatus.OK).body(updateRoleResponse);
    }

    @Operation(summary = "Delete a role",
            description = "Return a confirmation message. Only the users with ADMIN role can delete roles.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role deleted succesfully"),
            @ApiResponse(responseCode = "403", description = "Access Denied"),
            @ApiResponse(responseCode = "404", description = "Role not found"),
            @ApiResponse(responseCode = "500", description = "Invalid Token. (Unauthorized/Not Authenticated)")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRole(@PathVariable Long id){
        roleService.deleteRole(id);

        return ResponseEntity.status(HttpStatus.OK).body("Role deleted");
    }
}
