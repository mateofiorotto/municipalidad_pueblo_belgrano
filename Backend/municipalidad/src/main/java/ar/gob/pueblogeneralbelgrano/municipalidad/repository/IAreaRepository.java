package ar.gob.pueblogeneralbelgrano.municipalidad.repository;

import ar.gob.pueblogeneralbelgrano.municipalidad.model.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IAreaRepository extends JpaRepository<Area,Long> {
    @Query(value = "SELECT COUNT(*) FROM reclamos WHERE area_id=?1 AND deleted <> TRUE", nativeQuery = true)
    Long relatedAreas(Long id);

    Page<Area> findAllByOrderByIdAsc(Pageable pageable);

    boolean existsByNombre(String area);

    boolean existsByNombreAndIdNot(String area, Long id);
}
