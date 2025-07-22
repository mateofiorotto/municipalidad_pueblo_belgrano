package ar.gob.pueblogeneralbelgrano.municipalidad.mapper;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.news.NewsRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.news.NewsResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface INewsMapper {

    INewsMapper mapper = Mappers.getMapper(INewsMapper.class);

    NewsResponseDTO newsToNewsResponseDTO(News news);
    News newsResponseDTOToNews(NewsResponseDTO newsResponseDTO);

    NewsRequestDTO newsToNewsRequestDTO(News news);
    News newsRequestDTOToNews(NewsRequestDTO newsRequestDTO);
}
