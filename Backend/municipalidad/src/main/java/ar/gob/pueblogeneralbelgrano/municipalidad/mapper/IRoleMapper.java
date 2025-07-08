package ar.gob.pueblogeneralbelgrano.municipalidad.mapper;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.role.RoleResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.role.RoleRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IRoleMapper {

    IRoleMapper mapper = Mappers.getMapper(IRoleMapper.class);

    RoleResponseDTO roleToRoleGetDTO(Role role);
    Role roleGetDTOToRole(RoleResponseDTO roleGetDTO);

    RoleRequestDTO roleToRoleSaveDTO(Role role);
    Role roleSaveDTOToRole(RoleRequestDTO roleSaveDTO);
}
