package ar.gob.pueblogeneralbelgrano.municipalidad.controller;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.category.CategoryResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.news.NewsRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.news.NewsResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.response.ResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.service.news.INewsService;
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
@RequestMapping("/news")
@PreAuthorize("denyAll()")
@Validated
public class NewsController {

    private final INewsService newsService;

    public NewsController(INewsService newsService) {
        this.newsService = newsService;
    }

    /**
     * Endpoint que obtiene las noticias de manera paginada. Accedible por todos
     *
     * @param page
     * @return noticias paginadas de a 6 registros
     */
    @Operation(summary = "Obtener lista de noticias de forma paginada",
            description = "Devuelve la lista de noticias del sistema de a 6 registros. Accedible por todos",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noticias retornadas correctamente."),
    })
    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseDTO<PagedModel<NewsResponseDTO>>> getPaginatedNews(
            @RequestParam(value = "page", defaultValue = "0") int page) {

        final int size = 6; //Siempre 6 registros por pagina

        PagedModel<NewsResponseDTO> news = newsService.getPaginatedNews(page, size);

        ResponseDTO<PagedModel<NewsResponseDTO>> getResponseNews = new ResponseDTO<>(news, 200, "Noticias retornadas correctamente");

        return ResponseEntity.ok(getResponseNews);
    }

    /**
     * Endpoint que obtiene las noticias de manera paginada y categorizadas. Accedible por todos
     *
     * @param page
     * @return noticias categorizadas y paginadas de a 6 registros
     */
    @Operation(summary = "Obtener lista de noticias de forma paginada y categorizadas",
            description = "Devuelve la lista de noticias del sistema de a 6 registros. Accedible por todos",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noticias retornadas correctamente."),
    })
    @GetMapping("/category/{category_id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseDTO<PagedModel<NewsResponseDTO>>> getCategorizedAndPaginatedNews(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @PathVariable Long category_id) {

        final int size = 6;

        PagedModel<NewsResponseDTO> news = newsService.getNewsByCategoryAndPaginated(page, size, category_id);

        ResponseDTO<PagedModel<NewsResponseDTO>> getResponseNewsCategorized = new ResponseDTO<>(news, 200, "Noticias retornadas correctamente");

        return ResponseEntity.ok(getResponseNewsCategorized);
    }

    /**
     * Endpoint que obtiene las ultimas 3 noticias
     *
     * @return ultimas 3 noticias ordenadas por fecha
     */
    @Operation(summary = "Obtener una lista con las ultimas 3 noticias segun fecha e ID",
            description = "Devuelve una lista con las ultimas 3 noticias ordenadas por fecha e ID. Accedible por todos",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ultimas Noticias retornadas correctamente."),
    })
    @GetMapping("/last-three-news")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseDTO<List<NewsResponseDTO>>> getLastThreeNews(){
        List<NewsResponseDTO> lastThreeNews = newsService.getLastThreeNews();

        ResponseDTO<List<NewsResponseDTO>> getResponseLastThreeNews = new ResponseDTO<>(lastThreeNews, 200, "Ultimas tres noticias retornadas correctamente");

        return ResponseEntity.ok(getResponseLastThreeNews);
    }

    /**
     * Endpoint que obtiene una noticia por ID. accedible por todos
     * @param id
     * @return una noticia
     */
    @Operation(summary = "Obtener una noticia",
            description = "Obtener una noticia en particular. Accedible por todos",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noticia retornada correctamente"),
            @ApiResponse(responseCode = "404", description = "Noticia no encontrada"),
    })
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseDTO<NewsResponseDTO>> getNewsById(@PathVariable Long id){
        NewsResponseDTO newsFinded = newsService.getNewsById(id);

        ResponseDTO<NewsResponseDTO> newsFindedResponse = new ResponseDTO<>(newsFinded, 200, "Noticia retornada con exito");
        
        return ResponseEntity.ok(newsFindedResponse);
    }

    /**
     * Endpoint que guarda una noticia en la base de datos. Solo accedible por admins, el intendente o comunicacion
     * @param news
     * @return noticia creada
     */
    @Operation(summary = "Crear una noticia",
            description = "Retornar la noticia creada. Solo admins, el intendente o comunicacion pueden crear nuevas noticias.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Noticia creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'COMUNICACION')")
    public ResponseEntity<ResponseDTO<NewsRequestDTO>> saveNews(@Valid @RequestBody NewsRequestDTO news){

        newsService.saveNews(news);

        ResponseDTO<NewsRequestDTO> saveNewsResponse = new ResponseDTO<>(news,201,"Noticia creada con exito");

        return ResponseEntity.status(HttpStatus.CREATED).body(saveNewsResponse);
    }

    /**
     * Endpoint que actualiza una noticia de la base de datos. Solo accedible por admins, el intendente o comunicacion
     * @param news
     * @param id
     * @return noticia actualizada
     */
    @Operation(summary = "Editar una noticia",
            description = "Retornar la noticia editada. Solo usuarios admins, el intendente o comunicacion pueden editar noticias.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noticia editada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Noticia no encontrada"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'COMUNICACION')")
    public ResponseEntity<ResponseDTO<NewsRequestDTO>> updateNews(@Valid @RequestBody NewsRequestDTO news, @PathVariable Long id){

        newsService.updateNews(news, id);

        ResponseDTO<NewsRequestDTO> updateNewsResponse = new ResponseDTO<>(news, 200, "Noticia actualizada con exito");

        return ResponseEntity.ok(updateNewsResponse);
    }

    /**
     * Endpoint que elimina una noticia de la base de datos. Accedible solo por admins, el intendente o comunicacion
     *
     * @param id
     * @return mensaje de confirmacion
     */
    @Operation(summary = "Borrar una noticia",
            description = "Devuelve un mensaje de confirmacion. Solo admins, el intendente o comunicacion pueden borrar noticias.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noticia borrada correctamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Noticia no encontrada"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'COMUNICACION')")
    public ResponseEntity<Map<String, String>> deleteNews(@PathVariable Long id){
        newsService.deleteNews(id);

        return ResponseEntity.ok(Map.of("message", "Noticia eliminada con exito"));
    }

}
