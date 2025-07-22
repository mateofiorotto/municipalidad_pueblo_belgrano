package ar.gob.pueblogeneralbelgrano.municipalidad.controller;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.news.NewsRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.news.NewsResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.response.ResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.service.news.INewsService;
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
@RequestMapping("/")
public class NewsController {

    private final INewsService newsService;

    public NewsController(INewsService newsService) {
        this.newsService = newsService;
    }

    /**
     * Endpoint que obtiene la lista de noticias. Accedible por todos
     *
     * @return lista de noticias
     */
    @Operation(summary = "Obtener lista de noticias",
            description = "Devuelve la lista de noticias del sistema. Solo empleados municipales / administradores pueden acceder",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noticias retornadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)"),
    })
    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseDTO<List<NewsResponseDTO>>> getNews(){

        List<NewsResponseDTO> news = newsService.getNews();

        ResponseDTO<List<NewsResponseDTO>> getNewsResponse = new ResponseDTO<>(news, 200, "Noticias retornadas con exito");

        return ResponseEntity.ok(getNewsResponse);
    }

    /**
     * Endpoint que obtiene una noticia por ID. accedible por todos
     * @param id
     * @return una noticia
     */
    @Operation(summary = "Obtener una noticia",
            description = "Obtener una noticia en particular. Solo empleados municipales / administradores pueden acceder",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noticia retornada correctamente"),
            @ApiResponse(responseCode = "404", description = "Noticia no encontrada"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseDTO<NewsResponseDTO>> getNewsById(@PathVariable Long id){
        NewsResponseDTO newsFinded = newsService.getNewsById(id);

        ResponseDTO<NewsResponseDTO> newsFindedResponse = new ResponseDTO<>(newsFinded, 200, "Noticia retornada con exito");
        
        return ResponseEntity.ok(newsFindedResponse);
    }

    /**
     * Endpoint que guarda una noticia en la base de datos. Solo accedible por admins/empleados municipales
     * @param news
     * @return noticia creada
     */
    @Operation(summary = "Crear una noticia",
            description = "Retornar la noticia creada. Solo admins/empleados municipales pueden crear nuevas noticias.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Noticia creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO_MUNICIPAL')")
    public ResponseEntity<ResponseDTO<NewsRequestDTO>> saveNews(@Valid @RequestBody NewsRequestDTO news){

        newsService.saveNews(news);

        ResponseDTO<NewsRequestDTO> saveNewsResponse = new ResponseDTO<>(news,201,"Noticia creada con exito");

        return ResponseEntity.status(HttpStatus.CREATED).body(saveNewsResponse);
    }

    /**
     * Endpoint que actualiza una noticia de la base de datos. Solo accedible por admins y empleados municipales.
     * @param news
     * @param id
     * @return noticia actualizada
     */
    @Operation(summary = "Editar una noticia",
            description = "Retornar la noticia editada. Solo usuarios admins/empleados municipales pueden editar noticias.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noticia editada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Noticia no encontrada"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO_MUNICIPAL')")
    public ResponseEntity<ResponseDTO<NewsRequestDTO>> updateNews(@Valid @RequestBody NewsRequestDTO news, @PathVariable Long id){

        newsService.updateNews(news, id);

        ResponseDTO<NewsRequestDTO> updateNewsResponse = new ResponseDTO<>(news, 200, "Noticia actualizada con exito");

        return ResponseEntity.ok(updateNewsResponse);
    }

    /**
     * Endpoint que elimina una noticia de la base de datos. Accedible solo por admins y empleados municipales
     *
     * @param id
     * @return mensaje de confirmacion
     */
    @Operation(summary = "Borrar una noticia",
            description = "Devuelve un mensaje de confirmacion. Solo admins/empleados municipales pueden borrar noticias.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noticia borrada correctamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Noticia no encontrada"),
            @ApiResponse(responseCode = "500", description = "Token invalido (No autenticado / No autorizado)")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO_MUNICIPAL')")
    public ResponseEntity<String> deleteNews(@PathVariable Long id){
        newsService.deleteNews(id);

        return ResponseEntity.ok("Noticia eliminada con exito");
    }

}
