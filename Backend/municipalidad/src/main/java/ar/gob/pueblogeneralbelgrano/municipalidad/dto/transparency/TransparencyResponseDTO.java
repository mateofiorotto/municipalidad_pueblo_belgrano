package ar.gob.pueblogeneralbelgrano.municipalidad.dto.transparency;

import java.time.LocalDate;

public record TransparencyResponseDTO(
        LocalDate fecha,
        String pdf) {
}
