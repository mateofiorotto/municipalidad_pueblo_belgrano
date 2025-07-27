package ar.gob.pueblogeneralbelgrano.municipalidad.service.category;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.category.CategoryRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.category.CategoryResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.ConflictException;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.NotFoundException;
import ar.gob.pueblogeneralbelgrano.municipalidad.mapper.ICategoryMapper;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Category;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.ICategoryRepository;
import ar.gob.pueblogeneralbelgrano.municipalidad.service.category.ICategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
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
    public PagedModel<CategoryResponseDTO> getPaginatedCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Category> paginatedCategories = categoryRepository.findAllByOrderByIdAsc(pageable);

        List<CategoryResponseDTO> categoryDTOList = paginatedCategories
                .stream()
                .map(ICategoryMapper.mapper::categoryToCategoryResponseDTO)
                .collect(Collectors.toList());

        //crear un nuevo objeto page y le pasamos la lista de categorias transformadas y la info de paginas
        Page<CategoryResponseDTO> paginatedCategoriesList = new PageImpl<>(
                categoryDTOList,
                pageable,
                paginatedCategories.getTotalElements()
        );

        return new PagedModel<>(paginatedCategoriesList);
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

        if (categoryRepository.existsByNombre(category.nombre())) {
            throw new ConflictException("La categoria con el nombre " + category.nombre() + " ya existe");
        }

        Category categoryToSave = ICategoryMapper.mapper.categoryRequestDTOToCategory(category);

        categoryRepository.save(categoryToSave);

        return category;
    }

    @Override
    public CategoryRequestDTO updateCategory(CategoryRequestDTO category, Long id) {
        Category categoryToUpdate = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria no encontrada, ID: " + id));

        if (categoryRepository.existsByNombreAndIdNot(category.nombre(), id)) {
            throw new ConflictException("La categoria con el nombre " + category.nombre() + " ya existe");
        }

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
        }

        categoryRepository.delete(categoryToDelete);

    }
}
