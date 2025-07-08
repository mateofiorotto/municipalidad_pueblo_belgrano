package ar.gob.pueblogeneralbelgrano.municipalidad.service.permission;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.permission.PermissionRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.permission.PermissionResponseDTO;

import java.util.List;

public interface IPermissionService {
    /**
     * Returns a list of all permissions
     *
     * @return list of permissions
     * */
    public List<PermissionResponseDTO> getPermissions();

    /**
     * Returns a specific permission
     *
     * @param id
     * @return a permission
     * */
    public PermissionResponseDTO getPermissionById(Long id);

    /**
     * Create a permission
     *
     * @param permission
     * @return the created permission info
     * */
    public PermissionRequestDTO savePermission(PermissionRequestDTO permission);

    /**
     * Update a permission by id
     *
     * @param permission
     * @param id
     * @return the updated permission
     * */
    public PermissionRequestDTO updatePermission(PermissionRequestDTO permission, Long id);

    /**
     * Delete a permission
     * @param id
     * */
    public void deletePermission(Long id);
}
