package ar.gob.pueblogeneralbelgrano.municipalidad.dto.permission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PermissionRequestDTO(
    @NotBlank(message = "El permiso es requerido")
    @Size(min = 3, max = 20, message = "El permiso debe tener entre 3 y 20 caracteres")
    String permission
){};
