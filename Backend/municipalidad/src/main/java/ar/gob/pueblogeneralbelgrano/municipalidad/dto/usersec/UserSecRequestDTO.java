package ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.role.RoleIdDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record UserSecRequestDTO(
        @NotBlank(message = "El nombre de usuario es requerido") //si fuese un perfil no deberia poder editarse
        @Size(min = 3, max = 20, message = "El nombre de usuario debe estar entre 3 y 20 caracteres")
        String username,

        @NotBlank(message = "La contraseña es requerida")
        String password,

        @NotNull(message = "El campo 'enabled' no puede ser nulo")
        Boolean enabled,

        @NotEmpty(message = "Al menos un rol debe estar asignado")
        Set<RoleIdDTO> roles
) {}
