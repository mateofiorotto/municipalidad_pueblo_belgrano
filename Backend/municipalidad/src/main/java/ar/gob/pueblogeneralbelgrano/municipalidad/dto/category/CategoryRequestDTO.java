package ar.gob.pueblogeneralbelgrano.municipalidad.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(
        @NotBlank(message = "La categoria no puede estar vacía")
        @Size(min = 3, max = 100, message = "La categoria debe tener entre 3 y 100 caracteres")
        String nombre) {
}
