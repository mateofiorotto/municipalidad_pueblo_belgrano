package ar.gob.pueblogeneralbelgrano.municipalidad.dto.news;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.category.CategoryIdDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventIdDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewsRequestDTO(
        @NotBlank(message = "El titular no puede estar vacío")
        @Size(min = 15, max = 100, message = "El titular debe tener entre 15 y 100 caracteres")
        String titular,

        @NotBlank(message = "El subtitulo no puede estar vacío")
        @Size(min = 20, max = 255, message = "El subtitulo debe tener entre 20 y 255 caracteres")
        String subtitulo,

        @NotNull(message = "La fecha es obligatoria")
        LocalDate fecha,

        @NotBlank(message = "La URL de la imagen no puede estar vacía")
        String imagen,

        @NotBlank(message = "La descripción no puede estar vacía")
        @Size(min = 20, max = 1500, message = "La descripción debe tener entre 10 y 1500 caracteres")
        String descripcion,

        @Size(max = 1500, message = "La descripción adicional no puede superar los 1500 caracteres")
        String descripcion_adicional,

        @NotNull(message = "La categoría es obligatoria")
        CategoryIdDTO categoria,

        //evento es opcional
        EventIdDTO evento
) {
}
