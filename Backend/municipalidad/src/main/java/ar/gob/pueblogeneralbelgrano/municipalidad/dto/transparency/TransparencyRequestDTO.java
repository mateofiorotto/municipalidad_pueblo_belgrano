package ar.gob.pueblogeneralbelgrano.municipalidad.dto.transparency;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.Date;

public record TransparencyRequestDTO(
        @NotNull(message = "La fecha es obligatoria")
        LocalDate fecha,

        @NotBlank(message = "La URL del PDF no puede estar vacía")
        String pdf) {
}
