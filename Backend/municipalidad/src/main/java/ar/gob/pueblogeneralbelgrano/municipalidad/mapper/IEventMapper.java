package ar.gob.pueblogeneralbelgrano.municipalidad.mapper;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IEventMapper {

    IEventMapper mapper = Mappers.getMapper(IEventMapper.class);

    EventResponseDTO eventToEventResponseDTO(Event event);
    Event eventResponseDTOToEvent(EventResponseDTO eventResponseDTO);

    EventRequestDTO eventToEventRequestDTO(Event event);
    Event eventRequestDTOToEvent(EventRequestDTO eventRequestDTO);
}
