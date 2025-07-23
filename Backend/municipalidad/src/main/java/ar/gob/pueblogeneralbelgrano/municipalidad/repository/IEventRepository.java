package ar.gob.pueblogeneralbelgrano.municipalidad.repository;

import ar.gob.pueblogeneralbelgrano.municipalidad.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventRepository extends JpaRepository<Event,Long> {
    @Query(value = "SELECT COUNT(*) FROM noticias WHERE evento_id=?1 AND deleted <> 1",nativeQuery = true)
    Long relatedEvents(Long id);
}
