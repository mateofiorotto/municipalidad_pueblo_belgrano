package ar.gob.pueblogeneralbelgrano.municipalidad.controller;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.area.AreaRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.area.AreaResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.response.ResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.service.area.IAreaService;
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
@RequestMapping("/areas")
@PreAuthorize("denyAll()")
@Validated
public class AreaController {
    private final IAreaService areaService;

    public AreaController(IAreaService areaService) {
        this.areaService = areaService;
    }

    /**
     * Endpoint que obtiene todas las areas. Solo empleados municipales / administradores pueden acceder
     *
     * @return DTO De la lista de areas
     */
    @Operation(summary = "Obtener lista de areas",
            description = "Devuelve la lista de areas del sistema. Solo empleados municipales / administradores pueden acceder",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Areas retornadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)"),
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO_MUNICIPAL')")
    public ResponseEntity<ResponseDTO<List<AreaResponseDTO>>> getAreas() {
        List<AreaResponseDTO> areas = areaService.getAreas();

        ResponseDTO<List<AreaResponseDTO>> getResponseAreas = new ResponseDTO<>(areas, 200, "Areas retornadas correctamente");

        return ResponseEntity.ok(getResponseAreas);
    }

    /**
     * Endpoint que obtiene una area por su id. Solo empleados municipales / administradores pueden acceder
     *
     * @param id
     * @return Una area
     */
    @Operation(summary = "Obtener un area",
            description = "Obtener un area en particular. Solo empleados municipales / administradores pueden acceder",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Area retornada correctamente"),
            @ApiResponse(responseCode = "404", description = "Area no encontrada"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO_MUNICIPAL')")
    public ResponseEntity<ResponseDTO<AreaResponseDTO>> getAreaById(@PathVariable Long id){

        AreaResponseDTO area = areaService.getAreaById(id);

        ResponseDTO<AreaResponseDTO> getResponseArea = new ResponseDTO<>(area, 200, "Area retornada correctamente");

        return ResponseEntity.ok(getResponseArea);
    }

    /**
     * Endpoint que guarda una area en la base de datos. Solo pueden crearlas Administradores y empleados municipales.
     *
     * @param area
     * @return la area creada
     */
    @Operation(summary = "Crear un area",
            description = "Retornar el area creada. Solo admins/empleados municipales pueden crear nuevas areas.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Area creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO_MUNICIPAL')")
    public ResponseEntity<ResponseDTO<AreaRequestDTO>> saveArea(@Valid @RequestBody AreaRequestDTO area){
        areaService.saveArea(area);

        ResponseDTO<AreaRequestDTO> saveAreaResponse = new ResponseDTO<>(area, 200, "Area guardada con exito");

        return ResponseEntity.status(HttpStatus.CREATED).body(saveAreaResponse);
    }

    /**
     * Endpoint que edita una area de la base de datos. Solo accedible por admins y empleados municipales
     *
     * @param area
     * @param id
     * @return la edicion modificada
     */
    @Operation(summary = "Editar un area",
            description = "Retornar el area editada. Solo usuarios admins/empleados municipales pueden editar areas.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Area editada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Area no encontrada"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO_MUNICIPAL')")
    public ResponseEntity<ResponseDTO<AreaRequestDTO>> updateArea(@Valid @RequestBody AreaRequestDTO area, @PathVariable Long id){
        areaService.updateArea(area, id);

        ResponseDTO<AreaRequestDTO> updateAreaResponse = new ResponseDTO<>(area, 200, "Area editada correctamente");

        return ResponseEntity.status(HttpStatus.OK).body(updateAreaResponse);
    }

    /**
     * Endpoint que elimina una edicion de la DB. Solo accedible por admins / empleados municipales
     *
     * @param id
     * @return mensaje de confirmacion
     */
    @Operation(summary = "Borrar un area",
            description = "Devuelve un mensaje de confirmacion. Solo admins/empleados municipales pueden borrar areas.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Area borrada correctamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Area no encontrada"),
            @ApiResponse(responseCode = "409", description = "Error al eliminar area ya que esta relacionada con otra entidad"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO_MUNICIPAL')")
    public ResponseEntity<String> deleteArea(@PathVariable Long id){
        areaService.deleteArea(id);

        return ResponseEntity.status(HttpStatus.OK).body("Area eliminada");
    }
}
