package ar.gob.pueblogeneralbelgrano.municipalidad.controller;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.event.EventResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.response.ResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.service.event.IEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@PreAuthorize("denyAll()")
@Validated
public class EventController {

    private final IEventService eventService;

    public EventController(IEventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Endpoint que obtiene la lista de eventos. Todos pueden acceder
     *
     * @return lista de eventos
     */
    @Operation(summary = "Obtener lista de eventos",
            description = "Devuelve la lista de eventos del sistema. Solo empleados municipales / administradores pueden acceder",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos retornados correctamente"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)"),
    })
    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseDTO<List<EventResponseDTO>>> getEvents(){

        List<EventResponseDTO> events = eventService.getEvents();

        ResponseDTO<List<EventResponseDTO>> getResponseEvents = new ResponseDTO<>(events,200,"Eventos retornados con exito");

        return ResponseEntity.ok(getResponseEvents);
    }

    /**
     * Endpoint que devuelve un evento especifico por id. Todos pueden acceder
     *
     * @param id
     * @return un evento
     */
    @Operation(summary = "Obtener un evento",
            description = "Obtener un evento en particular. Solo empleados municipales / administradores pueden acceder",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento retornado correctamente"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseDTO<EventResponseDTO>> getEventById(@PathVariable Long id){

        EventResponseDTO event = eventService.getEventById(id);

        ResponseDTO<EventResponseDTO> getResponseEvent = new ResponseDTO<>(event, 200, "Evento retornado con exito");

        return ResponseEntity.ok(getResponseEvent);
    }

    /**
     * Endpoint que guarda un evento en la base de datos. Solo accedible por admins, el intendente y comunicacion.
     * @param event
     * @return evento creado
     */
    @Operation(summary = "Crear un evento",
            description = "Retornar el evento creado. Solo admins, el intendente y comunicacion pueden crear nuevos eventos.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'COMUNICACION')")
    public ResponseEntity<ResponseDTO<EventRequestDTO>> saveEvent(@Valid @RequestBody EventRequestDTO event){

        eventService.saveEvent(event);

        ResponseDTO<EventRequestDTO> saveEventResponse = new ResponseDTO<>(event,201,"Evento creado con exito");

        return ResponseEntity.status(HttpStatus.CREATED).body(saveEventResponse);
    }

    /**
     * Endpoint que actualiza un evento de la base de datos. Solo accedible por admins, el intendente y comunicacion.
     * @param event
     * @param id
     * @return evento actualizado
     */
    @Operation(summary = "Editar un evento",
            description = "Retornar el evento editado. Solo admins, el intendente y comunicacion pueden editar eventos.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento editado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'COMUNICACION')")
    public ResponseEntity<ResponseDTO<EventRequestDTO>> updateEvent(@Valid @RequestBody EventRequestDTO event, @PathVariable Long id){

        eventService.updateEvent(event, id);

        ResponseDTO<EventRequestDTO> updateEventResponse = new ResponseDTO<>(event, 200, "Evento actualizado con exito");

        return ResponseEntity.ok(updateEventResponse);
    }

    /**
     * Endpoint que elimina un evento de la base de datos. Solo admins, el intendente y comunicacion pueden borrar eventos
     *
     * @param id
     * @return mensaje de confirmacion
     */
    @Operation(summary = "Borrar un evento",
            description = "Devuelve un mensaje de confirmacion. Solo admins, el intendente y comunicacion pueden borrar eventos.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento borrado correctamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado"),
            @ApiResponse(responseCode = "409", description = "Error al eliminar por relacion con otra entidad"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'COMUNICACION')")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id){
        eventService.deleteEvent(id);

        return ResponseEntity.ok("Evento eliminado con exito");
    }
}
