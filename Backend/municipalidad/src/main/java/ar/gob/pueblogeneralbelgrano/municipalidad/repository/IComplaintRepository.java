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
    @Query(value = "SELECT * FROM reclamos WHERE deleted = FALSE AND (cerrado = FALSE OR (cerrado = TRUE AND fecha_reclamo > DATE_ADD(CURDATE(), INTERVAL -7 DAY))) ORDER BY fecha_reclamo ASC;", nativeQuery = true)
    Page<Complaint> findAllRecentOpenComplaints(Pageable pageable);

    //Obtener x id solo si no esta cerrado hace mas de 7 dias o no cerrados
    @Query(value = "SELECT * FROM reclamos WHERE deleted = FALSE AND id = ?1 AND (cerrado = FALSE OR (cerrado = TRUE AND fecha_reclamo > DATE_ADD(CURDATE(), INTERVAL -7 DAY)));", nativeQuery = true)
    Optional<Complaint> findValidComplaintById(Long id);

}
