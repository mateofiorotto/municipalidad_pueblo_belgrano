package ar.gob.pueblogeneralbelgrano.municipalidad.service.permission;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.permission.PermissionRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.permission.PermissionResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.ConflictException;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.NotFoundException;
import ar.gob.pueblogeneralbelgrano.municipalidad.mapper.IPermissionMapper;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Permission;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.IPermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService implements IPermissionService {

    private final IPermissionRepository permissionRepository;

    public PermissionService(IPermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<PermissionResponseDTO> getPermissions() {

        List<Permission> permissions = permissionRepository.findAll();

        return permissions.stream().map(permission -> IPermissionMapper.mapper.permissionToPermissionResponseDTO(permission)).collect(Collectors.toList());
    }

    @Override
    public PermissionResponseDTO getPermissionById(Long id) {
        Permission permission = permissionRepository.findById(id).orElseThrow(() -> new NotFoundException("Permiso no encontrado, ID: " + id));

        return IPermissionMapper.mapper.permissionToPermissionResponseDTO(permission);
    }

    @Override
    public PermissionRequestDTO savePermission(PermissionRequestDTO permission) {

        if (permissionRepository.existsByPermission(permission.permission())) {
            throw new ConflictException("El permiso ya existe con el nombre: " + permission.permission());
        }

        Permission permissionToSave = IPermissionMapper.mapper.permissionRequestDTOToPermission(permission);

        permissionRepository.save(permissionToSave);

        return permission;
    }

    @Override
    public PermissionRequestDTO updatePermission(PermissionRequestDTO permission, Long id) {
        Permission permissionToUpdate = permissionRepository.findById(id).orElseThrow(() -> new NotFoundException("Permiso no encontrado, ID: " + id));

        if (permissionRepository.existsByPermissionAndIdNot(permission.permission(), id)) {
            throw new ConflictException("El permiso ya existe con el nombre: " + permission.permission());
        }

        permissionToUpdate.setPermission(permission.permission());

        permissionRepository.save(permissionToUpdate);

        return permission;
    }

    @Override
    public void deletePermission(Long id) {
        Permission permissionToDelete = permissionRepository.findById(id).orElseThrow(() -> new NotFoundException("Permiso no encontrado, ID: " + id));

        permissionRepository.delete(permissionToDelete);
    }
}
