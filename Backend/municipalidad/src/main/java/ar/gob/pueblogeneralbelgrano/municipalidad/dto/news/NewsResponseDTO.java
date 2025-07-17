package ar.gob.pueblogeneralbelgrano.municipalidad.dto.news;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.category.CategoryIdDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventIdDTO;

import java.util.Date;

public record NewsResponseDTO(
    String titular,
    Date fecha,
    String imagen,
    String descripcion,
    String descripcion_adicional,
    CategoryIdDTO categoria,
    EventIdDTO evento){

}
