package ar.gob.pueblogeneralbelgrano.municipalidad.service.event;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.ConflictException;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.NotFoundException;
import ar.gob.pueblogeneralbelgrano.municipalidad.mapper.IEventMapper;
import ar.gob.pueblogeneralbelgrano.municipalidad.mapper.IEventMapper;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Event;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Event;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.IEventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService implements IEventService {

    private final IEventRepository eventRepository;

    public EventService(IEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public PagedModel<EventResponseDTO> getPaginatedEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Event> paginatedEvents = eventRepository.findAllByOrderByFechaAsc(pageable);

        List<EventResponseDTO> eventDTOList = paginatedEvents
                .stream()
                .map(IEventMapper.mapper::eventToEventResponseDTO)
                .collect(Collectors.toList());

        Page<EventResponseDTO> paginatedEventsList = new PageImpl<>(
                eventDTOList,
                pageable,
                paginatedEvents.getTotalElements()
        );

        return new PagedModel<>(paginatedEventsList);
    }

    @Override
    public PagedModel<EventResponseDTO> getPaginatedNextEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        LocalDate fecha = LocalDate.now().minusDays(1);

        Page<Event> paginatedNextEvents = eventRepository.findAllByFechaAfterOrderByFechaAsc(fecha, pageable);

        List<EventResponseDTO> eventDTOList = paginatedNextEvents
                .stream()
                .map(IEventMapper.mapper::eventToEventResponseDTO)
                .collect(Collectors.toList());

        Page<EventResponseDTO> paginatedNextEventsList = new PageImpl<>(
                eventDTOList,
                pageable,
                paginatedNextEvents.getTotalElements()
        );

        return new PagedModel<>(paginatedNextEventsList);
    }

    @Override
    public List<EventResponseDTO> getEvents() {
        List<Event> listEvents = eventRepository.findAll();

        return listEvents
                .stream()
                .map(event -> IEventMapper.mapper.eventToEventResponseDTO(event))
                .collect(Collectors.toList());
    }

    @Override
    public EventResponseDTO getEventById(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new NotFoundException("El evento no existe, ID: " + id));

        return IEventMapper.mapper.eventToEventResponseDTO(event);
    }

    @Override
    public EventRequestDTO saveEvent(EventRequestDTO event) {
        Event eventToSave = IEventMapper.mapper.eventRequestDTOToEvent(event);

        //metodo para img: (hacer)

       eventRepository.save(eventToSave);

        return event;
    }

    @Override
    public EventRequestDTO updateEvent(EventRequestDTO event, Long id) {
        Event eventToUpdate = eventRepository.findById(id).orElseThrow(() -> new NotFoundException("El evento no existe, ID: " + id));

        eventToUpdate.setTitular(event.titular());
        eventToUpdate.setFecha(event.fecha());
        eventToUpdate.setDescripcion(event.descripcion());

        if(event.descripcion_adicional() != null) {
            eventToUpdate.setDescripcion_adicional(event.descripcion_adicional());
        }

        eventToUpdate.setImagen(event.imagen());

        eventRepository.save(eventToUpdate);

        return event;
    }

    @Override
    public void deleteEvent(Long id) {
        Event eventToDelete = eventRepository.findById(id).orElseThrow(() -> new NotFoundException("El evento no existe, ID: " + id));

        if (eventRepository.relatedEvents(id) >= 1) {
            throw new ConflictException("No se puede eliminar el evento ya que se encuentra relacionado con una noticia");
        }

        //metodo de eliminacion de img del sistema:

        eventRepository.delete(eventToDelete);
    }
}
