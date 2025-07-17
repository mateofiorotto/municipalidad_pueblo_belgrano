package ar.gob.pueblogeneralbelgrano.municipalidad.dto.event;


import java.util.Date;

public record EventResponseDTO(
        String titular,
        Date fecha,
        String imagen,
        String descripcion,
        String descripcion_adicional
) {
}
