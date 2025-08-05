package ar.gob.pueblogeneralbelgrano.municipalidad.dto.news;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.category.CategoryIdDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.category.CategoryResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventIdDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventResponseDTO;

import java.time.LocalDate;

public record NewsResponseDTO(Long id,
    String titular,
    String subtitulo,
    LocalDate fecha,
    String imagen,
    String descripcion,
    String descripcion_adicional,
    CategoryResponseDTO categoria,
    EventResponseDTO evento){

}
