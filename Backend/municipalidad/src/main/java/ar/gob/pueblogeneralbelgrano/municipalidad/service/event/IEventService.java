package ar.gob.pueblogeneralbelgrano.municipalidad.service.event;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventResponseDTO;

import java.util.List;

public interface IEventService {
    /**
     * Retornar una lista de todos los eventos
     *
     * @return lista de eventos
     * */
    public List<EventResponseDTO> getEvents();

    /**
     * Retornar un evento especifico
     *
     * @param id
     * @return un evento
     * */
    public EventResponseDTO getEventById(Long id);

    /**
     * Crear un evento
     *
     * @param event
     * @return evento creado
     * */
    public EventRequestDTO saveEvent(EventRequestDTO event);

    /**
     * Actualizar evento por id
     *
     * @param event
     * @param id
     * @return evento actualizado
     * */
    public EventRequestDTO updateEvent(EventRequestDTO event, Long id);

    /**
     * Borrar evento
     * @param id
     * */
    public void deleteEvent(Long id);
}
