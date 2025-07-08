package ar.gob.pueblogeneralbelgrano.municipalidad.mapper;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecUpdateDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.UserSec;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IUserMapper {

    IUserMapper mapper = Mappers.getMapper(IUserMapper.class);

    UserSecResponseDTO userSecToUserSecGetDTO(UserSec userSec);
    UserSec userSecGetDTOToUserSec(UserSecResponseDTO userSecGetDTO);

    UserSecRequestDTO userSecToUserSecSaveDTO(UserSec userSec);
    UserSec userSecSaveDTOToUserSec(UserSecRequestDTO userSecSaveDTO);

    UserSecUpdateDTO userSecToUserSecUpdateDTO(UserSec userSec);
}
