package ar.gob.pueblogeneralbelgrano.municipalidad.dto.transparency;

import java.time.LocalDate;

public record TransparencyResponseDTO(
        Long id,
        LocalDate fecha,
        String pdf) {
}
