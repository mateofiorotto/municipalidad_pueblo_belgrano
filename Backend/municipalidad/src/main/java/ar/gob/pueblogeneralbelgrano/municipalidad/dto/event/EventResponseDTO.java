package ar.gob.pueblogeneralbelgrano.municipalidad.dto.event;


import java.time.LocalDate;

public record EventResponseDTO(
        Long id,
        String titular,
        LocalDate fecha,
        String imagen,
        String descripcion,
        String descripcion_adicional
) {
}
