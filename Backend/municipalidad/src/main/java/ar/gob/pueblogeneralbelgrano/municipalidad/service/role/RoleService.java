package ar.gob.pueblogeneralbelgrano.municipalidad.service.role;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.permission.PermissionIdDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.role.RoleRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.role.RoleResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.BadRequestException;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.NotFoundException;
import ar.gob.pueblogeneralbelgrano.municipalidad.mapper.IRoleMapper;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Permission;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Role;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.IPermissionRepository;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.IRoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService {

    private final IRoleRepository roleRepository;
    private final IPermissionRepository permissionRepository;

    public RoleService(IRoleRepository roleRepository, IPermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<RoleResponseDTO> getRoles() {
        List<Role> roles = roleRepository.findAll();

        return roles.stream().map(role -> IRoleMapper.mapper.roleToRoleGetDTO(role)).collect(Collectors.toList());
    }

    @Override
    public RoleResponseDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role not found with ID: " + id));

        return IRoleMapper.mapper.roleToRoleGetDTO(role);
    }

    @Override
    public RoleRequestDTO saveRole(RoleRequestDTO role) {
        Role roleToSave = IRoleMapper.mapper.roleSaveDTOToRole(role);

        Set<Permission> permissionList = new HashSet<>();
        for (PermissionIdDTO per : role.permissions()) {
            Permission existingPermission = permissionRepository.findById(per.id())
                    .orElseThrow(() -> new BadRequestException("Permission not found"));

            permissionList.add(existingPermission);
        }

        roleToSave.setPermissions(permissionList);
        roleRepository.save(roleToSave);

        return role;
    }

    @Override
    public RoleRequestDTO updateRole(RoleRequestDTO role, Long id) {
        Role roleToUpdate = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role not found with ID: " + id));

        roleToUpdate.setRole(role.role());

        Set<Permission> permissionList = new HashSet<>();
        for (PermissionIdDTO per : role.permissions()) {
            Permission existingPermission = permissionRepository.findById(per.id())
                    .orElseThrow(() -> new BadRequestException("Permission not found"));

            permissionList.add(existingPermission);
        }

        roleToUpdate.getPermissions().clear();
        roleToUpdate.getPermissions().addAll(permissionList);

        roleRepository.save(roleToUpdate);

        return role;
    }

    @Override
    public void deleteRole(Long id) {
        Role roleToDelete = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role not found with ID: " + id));

        roleToDelete.getPermissions().clear();

        roleRepository.delete(roleToDelete);
    }
}
