package ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.area.AreaIdDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.area.AreaRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.area.AreaResponseDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ComplaintUpdateDTO(

        //Se controlan desde el dashboard admin por empleados muni
        String comentario,
        AreaIdDTO area,
        Boolean cerrado

        //NO SE PUEDEN MODIFICAR X EL USUARIO:
        //Date fecha_cerrado
        //Date fecha_reclamo
) {
}
