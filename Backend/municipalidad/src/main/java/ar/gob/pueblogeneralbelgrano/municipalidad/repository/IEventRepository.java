package ar.gob.pueblogeneralbelgrano.municipalidad.repository;

import ar.gob.pueblogeneralbelgrano.municipalidad.model.Event;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface IEventRepository extends JpaRepository<Event,Long> {
    @Query(value = "SELECT COUNT(*) FROM noticias WHERE evento_id=?1 AND deleted <> TRUE",nativeQuery = true)
    Long relatedEvents(Long id);

    Page<Event> findAllByOrderByFechaAsc(Pageable pageable);

    Page<Event> findAllByFechaAfterOrderByFechaAsc(LocalDate fecha, Pageable pageable);
}
