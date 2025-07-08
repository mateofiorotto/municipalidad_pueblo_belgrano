package ar.gob.pueblogeneralbelgrano.municipalidad.service.user;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecUpdateDTO;

import java.util.List;

public interface IUserService {
    /**
     * Returns a list of all users
     *
     * @return list of users
     * */
    public List<UserSecResponseDTO> getUsers();

    /**
     * Returns a specific user
     *
     * @param id
     * @return a user
     * */
    public UserSecResponseDTO getUserById(Long id);

    /**
     * Create a user
     *
     * @param user
     * @return the created user info
     * */
    public UserSecRequestDTO saveUser(UserSecRequestDTO user);

    /**
     * Update a user by id
     *
     * @param user
     * @param id
     * @return the updated user
     * */
    public UserSecUpdateDTO updateUser(UserSecUpdateDTO user, Long id);

    /**
     * Delete a user
     * @param id
     * */
    public void deleteUser(Long id);

    /**
     * Encript the user password
     *
     * @param password
     * @return encripted password
     * */
    public String encriptPassword(String password);
}
