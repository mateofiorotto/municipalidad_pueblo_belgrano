package ar.gob.pueblogeneralbelgrano.municipalidad.dto.role;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.permission.PermissionIdDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

public record RoleRequestDTO (
    @NotBlank(message = "El rol es requerido")
    @Size(min = 3, max = 20, message = "El rol debe tener entre 3 y 20 caracteres")
    String role,
    @NotEmpty(message = "Al menos un permiso debe estar asignado")
    Set<PermissionIdDTO> permissions
){}
