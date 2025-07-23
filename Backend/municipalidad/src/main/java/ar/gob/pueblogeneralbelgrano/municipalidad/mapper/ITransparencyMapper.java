package ar.gob.pueblogeneralbelgrano.municipalidad.mapper;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.transparency.TransparencyRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.transparency.TransparencyResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Transparency;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ITransparencyMapper {

    ITransparencyMapper mapper = Mappers.getMapper(ITransparencyMapper.class);

    TransparencyResponseDTO transparencyToTransparencyResponseDTO(Transparency transparency);
    Transparency transparencyResponseDTOToTransparency(TransparencyResponseDTO transparencyResponseDTO);

    TransparencyRequestDTO transparencyToTransparencyRequestDTO(Transparency transparency);
    Transparency transparencyRequestDTOToTransparency(TransparencyRequestDTO transparencyRequestDTO);
}
