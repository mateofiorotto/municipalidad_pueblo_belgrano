package ar.gob.pueblogeneralbelgrano.municipalidad.service.news;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.news.NewsRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.news.NewsResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface INewsService {
    /**
     * Devolver lista de noticias
     * @return lista de noticias
     */
    public List<NewsResponseDTO> getNews();

    /**
     * Devolver una noticia por su ID
     * @param id
     * @return noticia por id
     */
    public NewsResponseDTO getNewsById(Long id);

    /**
     * Guardar noticia en la base de datos
     * @param news
     * @return la noticia guardada
     */
    public NewsRequestDTO saveNews(NewsRequestDTO news);

    /**
     * Actualizar noticia en la base de datos
     * @param news
     * @param id
     * @return la noticia actualizada
     */
    public NewsRequestDTO updateNews(NewsRequestDTO news, Long id);

    /**
     * Elimina una noticia en la base de datos
     * @param id
     */
    public void deleteNews(Long id);
}
