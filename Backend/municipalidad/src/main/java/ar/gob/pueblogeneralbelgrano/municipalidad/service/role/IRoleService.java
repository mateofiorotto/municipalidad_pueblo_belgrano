package ar.gob.pueblogeneralbelgrano.municipalidad.service.role;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.role.RoleRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.role.RoleResponseDTO;

import java.util.List;

public interface IRoleService {
    /**
     * Devuelve una lista de roles
     *
     * @return lista de roles
     * */
    public List<RoleResponseDTO> getRoles();

    /**
     * Devuelve un rol especifico por id
     *
     * @param id
     * @return un rol
     * */
    public RoleResponseDTO getRoleById(Long id);

    /**
     * Crear un rol
     *
     * @param role
     * @return rol creado
     * */
    public RoleRequestDTO saveRole(RoleRequestDTO role);

    /**
     * Actualizar rol por ID
     *
     * @param role
     * @param id
     * @return rol actualizado
     * */
    public RoleRequestDTO updateRole(RoleRequestDTO role, Long id);

    /**
     * Borrar rol
     *
     * @param id
     * */
    public void deleteRole(Long id);
}
