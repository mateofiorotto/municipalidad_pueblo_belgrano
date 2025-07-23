package ar.gob.pueblogeneralbelgrano.municipalidad.service.permission;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.permission.PermissionRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.permission.PermissionResponseDTO;

import java.util.List;

public interface IPermissionService {
    /**
     * Devolver lista de permisos
     *
     * @return lista de permisos
     * */
    public List<PermissionResponseDTO> getPermissions();

    /**
     * Devolver permiso especifico por id
     *
     * @param id
     * @return un permiso
     * */
    public PermissionResponseDTO getPermissionById(Long id);

    /**
     * Crear un permiso
     *
     * @param permission
     * @return permiso creado
     * */
    public PermissionRequestDTO savePermission(PermissionRequestDTO permission);

    /**
     * Actualizar permiso por ID
     *
     * @param permission
     * @param id
     * @return permiso actualizado
     * */
    public PermissionRequestDTO updatePermission(PermissionRequestDTO permission, Long id);

    /**
     * Borrar un permiso
     *
     * @param id
     * */
    public void deletePermission(Long id);
}
