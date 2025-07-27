package ar.gob.pueblogeneralbelgrano.municipalidad.repository;

import ar.gob.pueblogeneralbelgrano.municipalidad.model.UserSec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserSecRepository extends JpaRepository<UserSec, Long> {
    Optional<UserSec> findUserEntityByUsername(String username);

    Page<UserSec> findAllByOrderByIdAsc(Pageable pageable);

    //comprobar si el username ya existe
    boolean existsByUsername(String username);

    //para el update (comprueba si el username es igual al de la persona que se quiere actualizar)
    boolean existsByUsernameAndIdNot(String username, Long id);
}
