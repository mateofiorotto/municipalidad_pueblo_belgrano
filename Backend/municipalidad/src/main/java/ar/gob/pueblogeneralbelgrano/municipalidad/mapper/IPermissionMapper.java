package ar.gob.pueblogeneralbelgrano.municipalidad.mapper;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.permission.PermissionResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.permission.PermissionRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IPermissionMapper {

    IPermissionMapper mapper = Mappers.getMapper(IPermissionMapper.class);

    PermissionResponseDTO permissionToPermissionResponseDTO(Permission permission);
    Permission permissionResponseDTOToPermission(PermissionResponseDTO permissionResponseDTO);

    PermissionRequestDTO permissionToPermissionRequestDTO(Permission permission);
    Permission permissionRequestDTOToPermission(PermissionRequestDTO permissionRequestDTO);
}
