package ar.gob.pueblogeneralbelgrano.municipalidad.repository;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.category.CategoryResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface
ICategoryRepository extends JpaRepository<Category,Long> {
    @Query(value = "SELECT COUNT(*) FROM noticias WHERE categoria_id=?1 AND deleted <> TRUE",nativeQuery = true)
    Long relatedCategories(Long id);

    Page<Category> findAllByOrderByIdAsc(Pageable pageable);

    boolean existsByNombre(String category);

    boolean existsByNombreAndIdNot(String category, Long id);
}
