package ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.area.AreaResponseDTO;

import java.util.Date;

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
        Date fecha_cerrado,
        Date fecha_reclamo) {
}
