package ar.gob.pueblogeneralbelgrano.municipalidad.repository;

import ar.gob.pueblogeneralbelgrano.municipalidad.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INewsRepository extends JpaRepository<News,Long> {
    Page<News> findAllByOrderByFechaDesc(Pageable pageable);

    //Noticias por categoria
    Page<News> findAllByCategoriaIdOrderByFechaDesc(Long id, Pageable pageable);

    //ultimas 3 noticias
    @Query(value = "SELECT * FROM noticias WHERE deleted = FALSE ORDER BY fecha DESC, id DESC LIMIT 3", nativeQuery = true)
    List<News> findLastThreeNews();
}
