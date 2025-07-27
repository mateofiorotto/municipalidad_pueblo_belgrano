package ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.area.AreaResponseDTO;

import java.time.LocalDate;


public record ComplaintResponseDTO(
        String motivo,
        String nombre_apellido,
        String celular,
        String direccion,
        String email,
        String descripcion,
        String imagen,
        String comentario,
        AreaResponseDTO area,
        Boolean cerrado,
        LocalDate fecha_cerrado,
        LocalDate fecha_reclamo) {
}
