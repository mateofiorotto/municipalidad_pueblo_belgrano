package ar.gob.pueblogeneralbelgrano.municipalidad.dto.news;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.category.CategoryIdDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventIdDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record NewsRequestDTO(
        @NotBlank(message = "El titular no puede estar vacío")
        @Size(min = 5, max = 100, message = "El titular debe tener entre 5 y 100 caracteres")
        String titular,

        @NotNull(message = "La fecha es obligatoria")
        Date fecha,

        @NotBlank(message = "La referencia de imagen no puede estar vacía")
        @Size(max = 255, message = "La ruta de imagen no puede superar los 255 caracteres")
        String imagen,

        @NotBlank(message = "La descripción no puede estar vacía")
        @Size(min = 10, max = 1000, message = "La descripción debe tener entre 10 y 1000 caracteres")
        String descripcion,

        @Size(max = 1000, message = "La descripción adicional no puede superar los 1000 caracteres")
        String descripcion_adicional,

        @NotNull(message = "La categoría es obligatoria")
        CategoryIdDTO categoria,

        //evento es opcional
        EventIdDTO evento
) {
}
