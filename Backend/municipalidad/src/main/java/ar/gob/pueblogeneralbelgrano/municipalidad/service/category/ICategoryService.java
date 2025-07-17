package ar.gob.pueblogeneralbelgrano.municipalidad.service.category;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.category.CategoryRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.category.CategoryResponseDTO;

import java.util.List;

public interface ICategoryService {
    /**
     * Devolver todas las categorias
     *
     * @return lista de categorias
     * */
    public List<CategoryResponseDTO> getCategories();

    /**
     * Devolver una categoria especifica
     *
     * @param id
     * @return una categoria
     * */
    public CategoryResponseDTO getCategoryById(Long id);

    /**
     * Crear una categoria
     *
     * @param category
     * @return la categoria creada
     * */
    public CategoryRequestDTO saveCategory(CategoryRequestDTO category);

    /**
     * Actualizar una categoria segun su id
     *
     * @param category
     * @param id
     * @return la categoria actualizada
     * */
    public CategoryRequestDTO updateCategory(CategoryRequestDTO category, Long id);

    /**
     * Borrar una categoria
     * @param id
     * */
    public void deleteCategory(Long id);
}
