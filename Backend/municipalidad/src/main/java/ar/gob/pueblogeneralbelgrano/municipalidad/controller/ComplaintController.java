package ar.gob.pueblogeneralbelgrano.municipalidad.controller;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintUpdateDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.response.ResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.service.complaint.IComplaintService;
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
@PreAuthorize("denyAll()")
@Validated
@RequestMapping("/complaints")
public class ComplaintController {

    private final IComplaintService complaintService;

    public ComplaintController(IComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    /**
     * Endpoint que obtiene la lista de reclamos. Solo para admins o empleados municipales
     *
     * @return lista de reclamos
     */
    @Operation(summary = "Obtener lista de reclamos",
            description = "Devuelve la lista de reclamos del sistema. Solo empleados municipales / administradores pueden acceder",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reclamos retornados correctamente"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)"),
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO_MUNICIPAL')")
    public ResponseEntity<ResponseDTO<List<ComplaintResponseDTO>>> getComplaints(){

        List<ComplaintResponseDTO> complaints = complaintService.getComplaints();

        ResponseDTO<List<ComplaintResponseDTO>> getResponseComplaints = new ResponseDTO<>(complaints,200,"Reclamos retornados con exito");

        return ResponseEntity.ok(getResponseComplaints);
    }

    /**
     * Endpoint que devuelve un reclamo especifico por id. Solo empleados municipales o admins
     *
     * @param id
     * @return un reclamo
     */
    @Operation(summary = "Obtener un reclamo",
            description = "Obtener un reclamo en particular. Solo empleados municipales / administradores pueden acceder",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reclamo retornado correctamente"),
            @ApiResponse(responseCode = "404", description = "Reclamo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO_MUNICIPAL')")
    public ResponseEntity<ResponseDTO<ComplaintResponseDTO>> getComplaintById(@PathVariable Long id){

        ComplaintResponseDTO complaint = complaintService.getComplaintById(id);

        ResponseDTO<ComplaintResponseDTO> getResponseComplaint = new ResponseDTO<>(complaint, 200, "Reclamo retornado con exito");

        return ResponseEntity.ok(getResponseComplaint);
    }

    /**
     * Endpoint que guarda un reclamo en la base de datos. Todos pueden realizar reclamos
     * @param complaint
     * @return reclamo creado
     */
    @Operation(summary = "Crear un reclamo",
            description = "Retornar el reclamo creado. Todo el mundo puede crear reclamos.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reclamo creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseDTO<ComplaintRequestDTO>> saveComplaint(@Valid @RequestBody ComplaintRequestDTO complaint){

        complaintService.saveComplaint(complaint);

        ResponseDTO<ComplaintRequestDTO> saveComplaintResponse = new ResponseDTO<>(complaint,201,"Reclamo creado con exito");

        return ResponseEntity.status(HttpStatus.CREATED).body(saveComplaintResponse);
    }

    /**
     * Endpoint que actualiza un reclamo de la base de datos. Solo accedible por admins y empleados municipales.
     * @param complaint
     * @param id
     * @return reclamo actualizado
     */
    @Operation(summary = "Editar el area, el comentario y si se cierra o no el reclamo",
            description = "Retornar el reclamo editado. Solo usuarios admins/empleados municipales pueden editar reclamos no menores a 7 dias de su cierre.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reclamo editado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Reclamo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO_MUNICIPAL')")
    public ResponseEntity<ResponseDTO<ComplaintUpdateDTO>> updateComplaint(@Valid @RequestBody ComplaintUpdateDTO complaint, @PathVariable Long id){

        complaintService.updateComplaint(complaint, id);

        ResponseDTO<ComplaintUpdateDTO> updateComplaintResponse = new ResponseDTO<>(complaint, 200, "Reclamo actualizado con exito");

        return ResponseEntity.ok(updateComplaintResponse);
    }

    /**
     * Endpoint que elimina un reclamo de la base de datos. Accedible solo por admins
     *
     * @param id
     * @return mensaje de confirmacion
     */
    @Operation(summary = "Borrar un reclamo",
            description = "Devuelve un mensaje de confirmacion. Solo admins pueden borrar reclamos.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reclamo borrado correctamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Reclamo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteComplaint(@PathVariable Long id){
        complaintService.deleteComplaint(id);

        return ResponseEntity.ok("Reclamo eliminado con exito");
    }
}
