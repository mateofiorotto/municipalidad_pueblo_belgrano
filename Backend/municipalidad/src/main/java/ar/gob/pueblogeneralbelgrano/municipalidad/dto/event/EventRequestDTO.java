package ar.gob.pueblogeneralbelgrano.municipalidad.dto.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record EventRequestDTO(
        @NotBlank(message = "El titular no puede estar vacío")
        @Size(min = 5, max = 100, message = "El titular debe tener entre 5 y 100 caracteres")
        String titular,

        @NotNull(message = "La fecha es obligatoria")
        Date fecha,

        @NotBlank(message = "La imagen no puede estar vacía")
        @Size(max = 255, message = "La ruta o URL de la imagen no puede superar los 255 caracteres")
        String imagen,

        @NotBlank(message = "La descripción no puede estar vacía")
        @Size(min = 10, max = 1000, message = "La descripción debe tener entre 10 y 1000 caracteres")
        String descripcion,

        @Size(max = 1000, message = "La descripción adicional no puede superar los 1000 caracteres")
        String descripcion_adicional
) {
}
