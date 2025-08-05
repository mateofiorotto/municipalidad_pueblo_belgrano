package ar.gob.pueblogeneralbelgrano.municipalidad.dto.role;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.permission.PermissionResponseDTO;

import java.util.HashSet;
import java.util.Set;

public record RoleResponseDTO(
        Long id,
        String role,
        Set<PermissionResponseDTO> permissions) {
}
