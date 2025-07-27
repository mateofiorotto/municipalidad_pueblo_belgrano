package ar.gob.pueblogeneralbelgrano.municipalidad.service.usersec;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecResponseDTO;
import org.springframework.data.web.PagedModel;

public interface IUserSecService {
    /**
     * Devolver usuarios paginados
     *
     * @param page
     * @param size
     * @return usuarios paginados
     */
    public PagedModel<UserSecResponseDTO> getPaginatedUsers(int page, int size);

    /**
     * Devolver un usuario especifico por id
     *
     * @param id
     * @return un usuario
     * */
    public UserSecResponseDTO getUserById(Long id);

    /**
     * Crear usuario
     *
     * @param user
     * @return usuario creado
     * */
    public UserSecRequestDTO saveUser(UserSecRequestDTO user);

    /**
     * Actualizar un usuario por id
     *
     * @param user
     * @param id
     * @return usuario actualizado
     * */
    public UserSecRequestDTO updateUser(UserSecRequestDTO user, Long id);

    /**
     * Borrar usuario
     *
     * @param id
     * */
    public void deleteUser(Long id);

    /**
     * Encriptar contraseña del usuario
     *
     * @param password
     * @return Contraseña encriptada
     * */
    public String encriptPassword(String password);
}
