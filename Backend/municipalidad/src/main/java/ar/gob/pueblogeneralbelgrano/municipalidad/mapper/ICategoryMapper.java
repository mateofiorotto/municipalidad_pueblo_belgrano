package ar.gob.pueblogeneralbelgrano.municipalidad.mapper;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.category.CategoryRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.category.CategoryResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ICategoryMapper {

    ICategoryMapper mapper = Mappers.getMapper(ICategoryMapper.class);

    CategoryResponseDTO categoryToCategoryResponseDTO(Category category);
    Category categoryResponseDTOToCategory(CategoryResponseDTO categoryResponseDTO);

    CategoryRequestDTO categoryToCategoryRequestDTO(Category category);
    Category categoryRequestDTOToCategory(CategoryRequestDTO categoryRequestDTO);
}
