package ar.gob.pueblogeneralbelgrano.municipalidad.controller;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.transparency.TransparencyResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.transparency.TransparencyRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.transparency.TransparencyResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.response.ResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.TransparencyType;
import ar.gob.pueblogeneralbelgrano.municipalidad.service.transparency.ITransparencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transparencies")
@PreAuthorize("denyAll()")
@Validated
public class TransparencyController {

    private final ITransparencyService transparencyService;

    public TransparencyController(ITransparencyService transparencyService) {
        this.transparencyService = transparencyService;
    }

    /**
     * Endpoint que obtiene las transparencias de manera paginada. Accedible por intendente, secretaria y admins
     *
     * @param page
     * @return transparencias paginadas de a 6 registros
     */
    @Operation(summary = "Obtener lista de transparencias de forma paginada",
            description = "Devuelve la lista de transparencias del sistema de a 6 registros. Accedible por intendente, secretaria y admins",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transparencias retornadas correctamente."),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)"),
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'SECRETARIA')")
    public ResponseEntity<ResponseDTO<PagedModel<TransparencyResponseDTO>>> getPaginatedTransparencies(
            @RequestParam(value = "page", defaultValue = "0") int page) {

        final int size = 12;

        PagedModel<TransparencyResponseDTO> transparencies = transparencyService.getPaginatedTransparencies(page, size);

        ResponseDTO<PagedModel<TransparencyResponseDTO>> getResponseTransparencies = new ResponseDTO<>(transparencies, 200, "Transparencias retornadas correctamente");

        return ResponseEntity.ok(getResponseTransparencies);
    }

    /**
     * Endpoint que devuelve los tipos de transparencias
     * @return lista de transparencias
     */
    @Operation(summary = "Obtener lista de tipos de transparencias",
            description = "Obtener la lista de todos los tipos de transparencias. Accedible por admins, intendentes o secretaria",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Transparencias retornada correctamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)")
    })
    @GetMapping("/types")
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'SECRETARIA')")
    public ResponseEntity<List<TransparencyType>> getTransparencyTypesList(){
        return ResponseEntity.ok(transparencyService.transparencyTypesList());
    }

    /**
     * Endpoint que obtiene las transparencias filtradas por tipo de manera paginada. Accedible por todos
     *
     * @param type tipo de transparencia (ORDENANZAS o DECRETOS)
     * @param page número de página
     * @return transparencias paginadas filtradas por tipo
     */
    @Operation(summary = "Obtener lista de transparencias filtradas por tipo",
            description = "Devuelve la lista de transparencias filtradas por tipo de a 12 registros. Accedible por todos",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transparencias retornadas correctamente."),
            @ApiResponse(responseCode = "400", description = "Tipo de transparencia inválido"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)"),
    })
    @GetMapping("/type/{type}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseDTO<PagedModel<TransparencyResponseDTO>>> getTransparenciesByType(
            @PathVariable String type,
            @RequestParam(value = "page", defaultValue = "0") int page) {

        final int size = 12;

        PagedModel<TransparencyResponseDTO> transparencies = transparencyService.getTransparenciesByType(page, size, type);

        ResponseDTO<PagedModel<TransparencyResponseDTO>> getResponseTransparencies = new ResponseDTO<>(
                transparencies,
                200,
                "Transparencias de tipo " + type + " retornadas correctamente"
        );

        return ResponseEntity.ok(getResponseTransparencies);
    }

    /**
     * Endpoint que obtiene una transparencia por ID. accedible por todos
     * @param id
     * @return una transparencia
     */
    @Operation(summary = "Obtener una transparencia",
            description = "Obtener una transparencia en particular. Accedible por todos",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transparencia retornada correctamente"),
            @ApiResponse(responseCode = "404", description = "Transparencia no encontrada"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)")
    })
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseDTO<TransparencyResponseDTO>> getTransparencyById(@PathVariable Long id){
        TransparencyResponseDTO transparencyFinded = transparencyService.getTransparencyById(id);

        ResponseDTO<TransparencyResponseDTO> transparencyFindedResponse = new ResponseDTO<>(transparencyFinded, 200, "Transparencia retornada con exito");
        
        return ResponseEntity.ok(transparencyFindedResponse);
    }

    /**
     * Endpoint que guarda una transparencia en la base de datos. Solo accedible por admins, el intendente o secretarios
     * @param transparency
     * @return transparencia creada
     */
    @Operation(summary = "Crear una transparencia",
            description = "Retornar la transparencia creada. Solo admins, el intendente o secretarios pueden crear nuevas transparencias.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transparencia creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'SECRETARIA')")
    public ResponseEntity<ResponseDTO<TransparencyRequestDTO>> saveTransparency(@Valid @RequestBody TransparencyRequestDTO transparency){

        transparencyService.saveTransparency(transparency);

        ResponseDTO<TransparencyRequestDTO> saveTransparencyResponse = new ResponseDTO<>(transparency,201,"Transparencia creada con exito");

        return ResponseEntity.status(HttpStatus.CREATED).body(saveTransparencyResponse);
    }

    /**
     * Endpoint que actualiza una transparencia de la base de datos. Solo accedible por admins, el intendente o secretarios
     * @param transparency
     * @param id
     * @return transparencia actualizada
     */
    @Operation(summary = "Editar una transparencia",
            description = "Retornar la transparencia editada. Solo admins, el intendente o secretarios pueden editar transparencias.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transparencia editada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Transparencia no encontrada"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'SECRETARIA')")
    public ResponseEntity<ResponseDTO<TransparencyRequestDTO>> updateTransparency(@Valid @RequestBody TransparencyRequestDTO transparency, @PathVariable Long id){

        transparencyService.updateTransparency(transparency, id);

        ResponseDTO<TransparencyRequestDTO> updateTransparencyResponse = new ResponseDTO<>(transparency, 200, "Transparencia actualizada con exito");

        return ResponseEntity.ok(updateTransparencyResponse);
    }

    /**
     * Endpoint que elimina una transparencia de la base de datos. Accedible solo por admins, el intendente o secretarios
     *
     * @param id
     * @return mensaje de confirmacion
     */
    @Operation(summary = "Borrar una transparencia",
            description = "Devuelve un mensaje de confirmacion. Solo admins, el intendente o secretarios pueden borrar transparencias.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transparencia borrada correctamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Transparencia no encontrada"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'SECRETARIA')")
    public ResponseEntity<Map<String, String>> deleteTransparency(@PathVariable Long id){
        transparencyService.deleteTransparency(id);

        return ResponseEntity.ok(Map.of("message", "Transparencia eliminada con exito"));
    }

}
