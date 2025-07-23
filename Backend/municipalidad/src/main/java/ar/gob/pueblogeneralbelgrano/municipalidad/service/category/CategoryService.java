package ar.gob.pueblogeneralbelgrano.municipalidad.service.category;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.category.CategoryRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.category.CategoryResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.ConflictException;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.NotFoundException;
import ar.gob.pueblogeneralbelgrano.municipalidad.mapper.ICategoryMapper;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Category;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.ICategoryRepository;
import ar.gob.pueblogeneralbelgrano.municipalidad.service.category.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponseDTO> getCategories() {

        List<Category> categories = categoryRepository.findAll();

        return categories
                .stream()
                .map(category -> ICategoryMapper.mapper.categoryToCategoryResponseDTO(category))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Categoria no encontrada, ID: " + id));

        return ICategoryMapper.mapper.categoryToCategoryResponseDTO(category);
    }

    @Override
    public CategoryRequestDTO saveCategory(CategoryRequestDTO category) {
        Category categoryToSave = ICategoryMapper.mapper.categoryRequestDTOToCategory(category);

        categoryRepository.save(categoryToSave);

        return category;
    }

    @Override
    public CategoryRequestDTO updateCategory(CategoryRequestDTO category, Long id) {
        Category categoryToUpdate = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria no encontrada, ID: " + id));

        categoryToUpdate.setNombre(category.nombre());

        categoryRepository.save(categoryToUpdate);

        return category;
    }

    @Override
    public void deleteCategory(Long id) {
        Category categoryToDelete = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria no encontrada, ID: " + id));

        if (categoryRepository.relatedCategories(id) >= 1) {
            throw new ConflictException("No se puede eliminar la categoria ya que se encuentra relacionada con una noticia");
        } else {
            categoryRepository.delete(categoryToDelete);
        }
    }
}
