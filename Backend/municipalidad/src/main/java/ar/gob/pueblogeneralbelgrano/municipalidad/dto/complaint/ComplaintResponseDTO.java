package ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint;

public record ComplaintResponseDTO(
        String motivo,
        String nombre_apellido,
        String celular,
        String direccion,
        String email,
        String descripcion,
        String imagen,
        String comentario,
        String area,
        boolean cerrado) {
}
