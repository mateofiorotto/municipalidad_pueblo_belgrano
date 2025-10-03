package ar.gob.pueblogeneralbelgrano.municipalidad.dto.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EventRequestDTO(
        @NotBlank(message = "El titular no puede estar vacío")
        @Size(min = 15, max = 100, message = "El titular debe tener entre 15 y 100 caracteres")
        String titular,

        @NotNull(message = "La fecha es obligatoria")
        LocalDate fecha,

        @NotBlank(message = "La URL de la imagen no puede estar vacía")
        String imagen,

        @NotBlank(message = "La descripción no puede estar vacía")
        @Size(min = 10, max = 1000, message = "La descripción debe tener entre 10 y 1000 caracteres")
        String descripcion,

        @Size(max = 1000, message = "La descripción adicional no puede superar los 1000 caracteres")
        String descripcion_adicional
) {
}
