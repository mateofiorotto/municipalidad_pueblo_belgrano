package ar.gob.pueblogeneralbelgrano.municipalidad.mapper;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.UserSec;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IUserSecMapper {

    IUserSecMapper mapper = Mappers.getMapper(IUserSecMapper.class);

    UserSecResponseDTO userSecToUserSecResponseDTO(UserSec userSec);
    UserSec userSecResponseDTOToUserSec(UserSecResponseDTO userSecResponseDTO);

    UserSecRequestDTO userSecToUserSecRequestDTO(UserSec userSec);
    UserSec userSecRequestDTOToUserSec(UserSecRequestDTO userSecRequestDTO);
}
