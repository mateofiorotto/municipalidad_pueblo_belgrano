package ar.gob.pueblogeneralbelgrano.municipalidad.dto.area;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AreaRequestDTO(
        @NotBlank(message = "El area no puede estar vacía")
        @Size(min = 3, max = 100, message = "El area debe tener entre 3 y 100 caracteres")
        String nombre) {
}
