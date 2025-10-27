package ar.gob.pueblogeneralbelgrano.municipalidad.dto.transparency;

import ar.gob.pueblogeneralbelgrano.municipalidad.model.TransparencyType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Date;

public record TransparencyRequestDTO(
        @NotBlank(message = "El titulo no puede estar vacío")
        @Size(min = 3, max = 200, message = "El titulo debe tener entre 3 y 200 caracteres")
        String titulo,

        @NotNull(message = "La fecha es obligatoria")
        LocalDate fecha,

        @NotBlank(message = "La URL del PDF no puede estar vacía")
        String pdf,

        @NotNull(message = "El tipo de transparencia es obligatorio")
        TransparencyType tipo) {
}
