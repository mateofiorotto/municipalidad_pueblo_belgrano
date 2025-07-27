package ar.gob.pueblogeneralbelgrano.municipalidad.dto.news;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.category.CategoryIdDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventIdDTO;

import java.time.LocalDate;

public record NewsResponseDTO(
    String titular,
    String subtitulo,
    LocalDate fecha,
    String imagen,
    String descripcion,
    String descripcion_adicional,
    CategoryIdDTO categoria,
    EventIdDTO evento){

}
