package ar.gob.pueblogeneralbelgrano.municipalidad.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequestDTO(
        @NotBlank(message = "Usuario requerido")
        String username,
        @NotBlank(message = "Contraseña requerida")
        String password,
        String captcha
        ) {
}
