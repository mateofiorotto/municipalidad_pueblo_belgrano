package ar.gob.pueblogeneralbelgrano.municipalidad.controller;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.response.ResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.service.event.IEventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("denyAll()")
@RequestMapping("/events")
public class EventController {

    IEventService eventService;

    public EventController(IEventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Endpoint que obtiene la lista de eventos. Todo el mundo puede acceder
     *
     * @return lista de eventos
     */
    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseDTO<List<EventResponseDTO>>> getEvents(){

        List<EventResponseDTO> events = eventService.getEvents();

        ResponseDTO<List<EventResponseDTO>> getResponseEvents = new ResponseDTO<>(events,200,"Eventos retornados con exito");

        return ResponseEntity.ok(getResponseEvents);
    }

    /**
     * Endpoint que devuelve un evento especifico por id. Todo el mundo puede acceder
     *
     * @param id
     * @return un evento
     */
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseDTO<EventResponseDTO>> getEventById(@PathVariable Long id){

        EventResponseDTO event = eventService.getEventById(id);

        ResponseDTO<EventResponseDTO> getResponseEvent = new ResponseDTO<>(event, 200, "Evento retornado con exito");

        return ResponseEntity.ok(getResponseEvent);
    }

    /**
     * Endpoint que guarda un evento en la base de datos. Solo accedible por admins/empleados municipales
     * @param event
     * @return evento creado
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO_MUNICIPAL')")
    public ResponseEntity<ResponseDTO<EventRequestDTO>> saveEvent(@Valid @RequestBody EventRequestDTO event){

        eventService.saveEvent(event);

        ResponseDTO<EventRequestDTO> saveEventResponse = new ResponseDTO<>(event,201,"Evento creado con exito");

        return ResponseEntity.status(HttpStatus.CREATED).body(saveEventResponse);
    }

    //update y delete. Coemntar y agregar swagger

}
