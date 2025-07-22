package ar.gob.pueblogeneralbelgrano.municipalidad.mapper;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.area.AreaRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.area.AreaResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Area;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IAreaMapper {

    IAreaMapper mapper = Mappers.getMapper(IAreaMapper.class);

    AreaResponseDTO areaToAreaResponseDTO(Area area);
    Area areaResponseDTOToArea(AreaResponseDTO areaResponseDTO);

    AreaRequestDTO areaToAreaRequestDTO(Area area);
    Area areaRequestDTOToArea(AreaRequestDTO areaRequestDTO);
}
