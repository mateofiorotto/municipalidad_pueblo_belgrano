package ar.gob.pueblogeneralbelgrano.municipalidad.controller;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintUpdateDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.response.ResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.service.captcha.ICaptchaService;
import ar.gob.pueblogeneralbelgrano.municipalidad.service.complaint.IComplaintService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("denyAll()")
@Validated
@RequestMapping("/complaints")
public class ComplaintController {

    private final IComplaintService complaintService;
    private final ICaptchaService captchaService;
    private final Bucket bucket;

    public ComplaintController(IComplaintService complaintService, ICaptchaService captchaService) {
        this.complaintService = complaintService;
        this.captchaService = captchaService;

        Bandwidth limit = Bandwidth.classic(1, Refill.greedy(1, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }


    /**
     * Endpoint que obtiene las reclamos de manera paginada. Accedible por admins, intendente o responsable de reclamos
     *
     * @param page
     * @return reclamos paginadas de a 6 registros
     */
    @Operation(summary = "Obtener lista de reclamos de forma paginada",
            description = "Devuelve la lista de reclamos del sistema de a 6 registros. Accedible por admins, intendente o responsable de reclamos",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reclamos retornados correctamente."),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)"),
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'RESPONSABLE_RECLAMOS')")
    public ResponseEntity<ResponseDTO<PagedModel<ComplaintResponseDTO>>> getPaginatedComplaints(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "status", defaultValue = "", required = false) String status
    ) {

        final int size = 6;

        PagedModel<ComplaintResponseDTO> complaints = complaintService.getPaginatedComplaints(page, size, status);

        ResponseDTO<PagedModel<ComplaintResponseDTO>> getResponseComplaints = new ResponseDTO<>(complaints, 200, "Reclamos retornados correctamente");

        return ResponseEntity.ok(getResponseComplaints);
    }

    /**
     * Endpoint que obtiene las reclamos de un area manera paginada. Accedible por admins, intendente o responsable de reclamos
     *
     * @param page
     * @return reclamos paginados por area de a 6 registros
     */
    @Operation(summary = "Obtener lista de reclamos segun un area de forma paginada",
            description = "Devuelve la lista de reclamos del sistema segun un area de a 6 registros. Accedible por admins, intendente o responsable de reclamos",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reclamos por arearetornados correctamente."),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)"),
    })
    @GetMapping("/area")
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'RESPONSABLE_RECLAMOS')")
    public ResponseEntity<ResponseDTO<PagedModel<ComplaintResponseDTO>>> getPaginatedComplaintsByArea(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam Long area_id
    ) {

        final int size = 6;

        PagedModel<ComplaintResponseDTO> complaints = complaintService.getPaginatedComplaintsByArea(page, size, area_id);

        ResponseDTO<PagedModel<ComplaintResponseDTO>> getResponseComplaintsByArea = new ResponseDTO<>(complaints, 200, "Reclamos por area retornados correctamente");

        return ResponseEntity.ok(getResponseComplaintsByArea);
    }

    /**
     * Endpoint que devuelve un reclamo especifico por id. Solo puede acceder el intendente, admins o responsables de reclamos
     *
     * @param id
     * @return un reclamo
     */
    @Operation(summary = "Obtener un reclamo",
            description = "Obtener un reclamo en particular. Solo pueden acceder admins, el intendente o responsables de reclamos",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reclamo retornado correctamente"),
            @ApiResponse(responseCode = "404", description = "Reclamo no encontrado"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'RESPONSABLE_RECLAMOS')")
    public ResponseEntity<ResponseDTO<ComplaintResponseDTO>> getComplaintById(@PathVariable Long id){

        ComplaintResponseDTO complaint = complaintService.getComplaintById(id);

        ResponseDTO<ComplaintResponseDTO> getResponseComplaint = new ResponseDTO<>(complaint, 200, "Reclamo retornado con exito");

        return ResponseEntity.ok(getResponseComplaint);
    }

    @GetMapping("/pdf/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'RESPONSABLE_RECLAMOS')")
    public ResponseEntity<byte[]> getComplaintPDF(@PathVariable Long id) {
        byte[] pdfBytes = complaintService.generateComplaintPDF(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "Reclamo_" + id + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
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
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)")
    })
    @PostMapping("/save")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseDTO<ComplaintRequestDTO>> saveComplaint(@Valid @RequestBody ComplaintRequestDTO complaint, HttpServletRequest request){

        //recaptcha
        String response = request.getParameter("g-recaptcha-response");
        captchaService.processResponse(complaint.captcha(), request);

        complaintService.saveComplaint(complaint, request);

        ResponseDTO<ComplaintRequestDTO> saveComplaintResponse = new ResponseDTO<>(complaint,201,"Reclamo creado con exito");

        return ResponseEntity.status(HttpStatus.CREATED).body(saveComplaintResponse);

    }

    /**
     * Endpoint que actualiza un reclamo de la base de datos. Solo accedible por admins, el intendente y responsable de reclamos
     * @param complaint
     * @param id
     * @return reclamo actualizado
     */
    @Operation(summary = "Editar el area, el comentario y si se cierra o no el reclamo",
            description = "Retornar el reclamo editado. Solo usuarios admins, el intendente o responsable de reclamos pueden editar reclamos no menores a 7 dias de su cierre.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reclamo editado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Reclamo no encontrado o pasaron 7 dias desde el reclamo"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'RESPONSABLE_RECLAMOS')")
    public ResponseEntity<ResponseDTO<ComplaintUpdateDTO>> updateComplaint(@Valid @RequestBody ComplaintUpdateDTO complaint, @PathVariable Long id){

        complaintService.updateComplaint(complaint, id);

        ResponseDTO<ComplaintUpdateDTO> updateComplaintResponse = new ResponseDTO<>(complaint, 200, "Reclamo actualizado con exito");

        return ResponseEntity.ok(updateComplaintResponse);
    }

    /**
     * Endpoint que elimina un reclamo de la base de datos. Accedible solo por el intendente o administradores
     *
     * @param id
     * @return mensaje de confirmacion
     */
    @Operation(summary = "Borrar un reclamo",
            description = "Devuelve un mensaje de confirmacion. Solo admins o el intendente pueden borrar reclamos.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reclamo borrado correctamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Reclamo no encontrado"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE')")
    public ResponseEntity<Map<String,String>> deleteComplaint(@PathVariable Long id){
        complaintService.deleteComplaint(id);

        return ResponseEntity.ok(Map.of("message", "Reclamo eliminado con exito"));
    }
}
