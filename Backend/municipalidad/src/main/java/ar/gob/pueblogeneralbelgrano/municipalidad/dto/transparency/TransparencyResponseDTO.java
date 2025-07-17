package ar.gob.pueblogeneralbelgrano.municipalidad.dto.transparency;

import java.util.Date;

public record TransparencyResponseDTO(
        Date fecha,
        String pdf) {
}
