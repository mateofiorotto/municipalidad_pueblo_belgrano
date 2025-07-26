package ar.gob.pueblogeneralbelgrano.municipalidad.repository;


import ar.gob.pueblogeneralbelgrano.municipalidad.model.Complaint;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.UserSec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IComplaintRepository extends JpaRepository<Complaint, Long> {
    //lista de reclamos. Muestra cerrados hace menos de 7 dias o no cerrados
    @Query(value = "SELECT * FROM reclamos WHERE cerrado = FALSE OR (cerrado = TRUE AND fecha_cerrado > DATE_ADD(NOW(), INTERVAL -7 DAY));", nativeQuery = true)
    List<Complaint> findAllRecentOpenComplaints();

    //Obtener x id solo si no esta cerrado hace mas de 7 dias o no cerrados
    @Query(value = "SELECT * FROM reclamos WHERE id = ?1 AND (cerrado = FALSE OR (cerrado = TRUE AND fecha_cerrado > DATE_ADD(NOW(), INTERVAL -7 DAY)));", nativeQuery = true)
    Optional<Complaint> findValidComplaintById(Long id);

    Page<Complaint> findAllByOrderByIdDesc(Pageable pageable);
}
