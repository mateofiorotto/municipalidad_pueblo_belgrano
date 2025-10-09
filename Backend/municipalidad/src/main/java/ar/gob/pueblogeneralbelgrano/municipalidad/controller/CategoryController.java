package ar.gob.pueblogeneralbelgrano.municipalidad.controller;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.category.CategoryRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.category.CategoryResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.response.ResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Category;
import ar.gob.pueblogeneralbelgrano.municipalidad.service.category.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
@PreAuthorize("denyAll()")
@Validated
public class CategoryController {
    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Endpoint que obtiene las categorias de manera paginada. Solo accedible admins, el intendente y comunicacion (solo se muestra en el dashboard)
     *
     * @param page
     * @return categorias paginadas de a 6 registros
     */
    @Operation(summary = "Obtener lista de categorias de forma paginada",
            description = "Devuelve la lista de categorias del sistema de a 6 registros. Solo accedible admins, el intendente y comunicacion (solo se muestra en el dashboard)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorias retornadas correctamente."),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)"),
    })
    @GetMapping("/paginado")
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'COMUNICACION')")
    public ResponseEntity<ResponseDTO<PagedModel<CategoryResponseDTO>>> getPaginatedCategories(
            @RequestParam(value = "page", defaultValue = "0") int page) {

        final int size = 6; //Siempre 6 registros por pagina

        PagedModel<CategoryResponseDTO> categories = categoryService.getPaginatedCategories(page, size);

        ResponseDTO<PagedModel<CategoryResponseDTO>> getResponseCategories = new ResponseDTO<>(categories, 200, "Categorias retornadas correctamente");

        return ResponseEntity.ok(getResponseCategories);
    }


    /**
     * Endpoint que obtiene todas las categorias. Solo accedible admins, el intendente y comunicacion (solo se muestra en el dashboard)
     * Será utilizado para cargar lista de categorias al momento de crear noticias y asignar categorias
     *
     * @return DTO De la lista de categorias
     */
    @Operation(summary = "Obtener lista de categorias",
            description = "Devuelve la lista de categorias del sistema. Solo accedible admins, el intendente y comunicacion (solo se muestra en el dashboard)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorias retornadas correctamente."),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)"),
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'COMUNICACION')")
    public ResponseEntity<ResponseDTO<List<CategoryResponseDTO>>> getCategories() {
        List<CategoryResponseDTO> categories = categoryService.getCategories();

        ResponseDTO<List<CategoryResponseDTO>> getResponseCategories = new ResponseDTO<>(categories, 200, "Categorias retornadas correctamente");

        return ResponseEntity.ok(getResponseCategories);
    }

    /**
     * Endpoint que obtiene una categoria por su id. Cualquiera puede acceder.
     *
     * @param id
     * @return Una categoria
     */
    @Operation(summary = "Obtener una categoria",
            description = "Obtener una categoria en particular. Cualquiera puede acceder (se muestra en cada noticia por individual)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria retornada correctamente."),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)")
    })
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseDTO<CategoryResponseDTO>> getCategoryById(@PathVariable Long id){

        CategoryResponseDTO category = categoryService.getCategoryById(id);

        ResponseDTO<CategoryResponseDTO> getResponseCategory = new ResponseDTO<>(category, 200, "Categoria retornada correctamente");

        return ResponseEntity.ok(getResponseCategory);
    }

    /**
     * Endpoint que guarda una categoria en la base de datos. Solo pueden crearlas admins, el intendente y comunicacion
     *
     * @param category
     * @return la categoria creada
     */
    @Operation(summary = "Crear una categoria",
            description = "Retornar la categoria creada. Solo admins, el intendente y comunicacion pueden crear nuevas categorias.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'COMUNICACION')")
    public ResponseEntity<ResponseDTO<CategoryRequestDTO>> saveCategory(@Valid @RequestBody CategoryRequestDTO category){
        categoryService.saveCategory(category);

        ResponseDTO<CategoryRequestDTO> saveCategoryResponse = new ResponseDTO<>(category, 200, "Categoria guardada con exito");

        return ResponseEntity.status(HttpStatus.CREATED).body(saveCategoryResponse);
    }

    /**
     * Endpoint que edita una categoria de la base de datos. Solo accedible por admins, el intendente y comunicacion
     *
     * @param category
     * @param id
     * @return la edicion modificada
     */
    @Operation(summary = "Editar una categoria",
            description = "Retornar la categoria editado. Solo usuarios admins, el intendente o comunicacion puede editar categorias.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria editada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en campos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'COMUNICACION')")
    public ResponseEntity<ResponseDTO<CategoryRequestDTO>> updateCategory(@Valid @RequestBody CategoryRequestDTO category, @PathVariable Long id){
        categoryService.updateCategory(category, id);

        ResponseDTO<CategoryRequestDTO> updateCategoryResponse = new ResponseDTO<>(category, 200, "Categoria editada correctamente");

        return ResponseEntity.status(HttpStatus.OK).body(updateCategoryResponse);
    }

    /**
     * Endpoint que elimina una edicion de la DB. Solo accedible por admins, el intendente o comunicacion
     *
     * @param id
     * @return mensaje de confirmacion
     */
    @Operation(summary = "Borrar una categoria",
            description = "Devuelve un mensaje de confirmacion. Solo admins, el intendente o comunicacion pueden borrar categorias.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria borrada correctamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada"),
            @ApiResponse(responseCode = "409", description = "Error al eliminar por relacion con otra entidad"),
            @ApiResponse(responseCode = "401", description = "Token invalido (No autenticado / No autorizado)")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INTENDENTE', 'COMUNICACION')")
    public ResponseEntity<Map<String, String>> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);

        return ResponseEntity.ok(Map.of("message", "Categoria eliminada con exito"));
    }
}
