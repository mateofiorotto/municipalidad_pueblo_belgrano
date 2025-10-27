package ar.gob.pueblogeneralbelgrano.municipalidad.dto.transparency;

import java.time.LocalDate;

public record TransparencyResponseDTO(
        Long id,
        String titulo,
        LocalDate fecha,
        String pdf,
        String tipo) {
}
