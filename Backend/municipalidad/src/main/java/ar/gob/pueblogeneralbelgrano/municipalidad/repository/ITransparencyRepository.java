package ar.gob.pueblogeneralbelgrano.municipalidad.repository;

import ar.gob.pueblogeneralbelgrano.municipalidad.model.Transparency;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.TransparencyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITransparencyRepository extends JpaRepository<Transparency, Long> {
    Page<Transparency> findAllByOrderByFechaDesc(Pageable pageable);

    @Query("SELECT DISTINCT t.tipo FROM Transparency t WHERE t.tipo IS NOT NULL")
    List<TransparencyType> findDistinctTypes();

    Page<Transparency> findAllByTipoOrderByFechaDesc(TransparencyType type, Pageable pageable);

}
