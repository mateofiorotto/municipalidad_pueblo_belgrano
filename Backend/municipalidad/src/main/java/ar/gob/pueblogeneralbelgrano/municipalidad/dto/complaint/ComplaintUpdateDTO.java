package ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.area.AreaIdDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.area.AreaRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.area.AreaResponseDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ComplaintUpdateDTO(
        String comentario,
        AreaIdDTO area,
        Boolean cerrado
) {
}
