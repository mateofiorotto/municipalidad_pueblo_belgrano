package ar.gob.pueblogeneralbelgrano.municipalidad.service.event;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventResponseDTO;
import org.springframework.data.web.PagedModel;

import java.util.List;

public interface IEventService {
    /**
     * Devolver eventos paginados
     *
     * @param page
     * @param size
     * @return eventos paginados
     */
    public PagedModel<EventResponseDTO> getPaginatedEvents(int page, int size);

    /**
     * Devolver solo eventos proximos paginados
     *
     * @param page
     * @param size
     * @return
     */
    public PagedModel<EventResponseDTO> getPaginatedNextEvents(int page, int size);

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
