package ar.gob.pueblogeneralbelgrano.municipalidad.service.event;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.NotFoundException;
import ar.gob.pueblogeneralbelgrano.municipalidad.mapper.IEventMapper;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Event;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.IEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService implements IEventService {

    IEventRepository eventRepository;

    public EventService(IEventRepository eventRepository) {
        this.eventRepository = eventRepository;
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
        eventToUpdate.setDescripcion_adicional(event.descripcion_adicional());

        //metodo para img:

        eventToUpdate.setImagen(event.imagen());

        return event;
    }

    @Override
    public void deleteEvent(Long id) {
        Event eventToDelete = eventRepository.findById(id).orElseThrow(() -> new NotFoundException("El evento no existe, ID: " + id));

        //metodo de eliminacion de img del sistema:

        eventRepository.delete(eventToDelete);
    }
}
