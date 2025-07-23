package ar.gob.pueblogeneralbelgrano.municipalidad.repository;

import ar.gob.pueblogeneralbelgrano.municipalidad.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IAreaRepository extends JpaRepository<Area,Long> {
    @Query(value = "SELECT COUNT(*) FROM reclamos WHERE area_id=?1 AND deleted <> 1", nativeQuery = true)
    Long relatedAreas(Long id);
}
