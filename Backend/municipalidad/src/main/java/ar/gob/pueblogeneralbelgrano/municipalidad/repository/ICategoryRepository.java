package ar.gob.pueblogeneralbelgrano.municipalidad.repository;

import ar.gob.pueblogeneralbelgrano.municipalidad.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category,Long> {
    @Query(value = "SELECT COUNT(*) FROM noticias WHERE categoria_id=?1 AND deleted <> 1",nativeQuery = true)
    int relatedCategories(Long id);
}
