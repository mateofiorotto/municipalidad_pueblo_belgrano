package ar.gob.pueblogeneralbelgrano.municipalidad.service.news;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.news.NewsRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.news.NewsResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.NotFoundException;
import ar.gob.pueblogeneralbelgrano.municipalidad.mapper.INewsMapper;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Category;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.News;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.ICategoryRepository;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.IEventRepository;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.INewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService implements INewsService {

    private final INewsRepository newsRepository;
    private final ICategoryRepository categoryRepository;
    private final IEventRepository eventRepository;

    public NewsService(INewsRepository newsRepository, ICategoryRepository categoryRepository, IEventRepository eventRepository) {
        this.newsRepository = newsRepository;
        this.categoryRepository = categoryRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<NewsResponseDTO> getNews() {
        List<News> newsList = newsRepository.findAll();

        return newsList
                .stream()
                .map(news -> INewsMapper.mapper.newsToNewsResponseDTO(news))
                .collect(Collectors.toList());
    }

    @Override
    public NewsResponseDTO getNewsById(Long id) {
        News news = newsRepository.findById(id).orElseThrow(() -> new NotFoundException("Noticia no encontrada,  ID" + id));

        return INewsMapper.mapper.newsToNewsResponseDTO(news);
    }

    @Override
    public NewsRequestDTO saveNews(NewsRequestDTO news) {
        News newsToSave = INewsMapper.mapper.newsRequestDTOToNews(news);

        //metodo img..

        newsRepository.save(newsToSave);

        return news;
    }

    @Override
    public NewsRequestDTO updateNews(NewsRequestDTO news, Long id) {
        News newsFinded = newsRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontro la noticia, ID: " + id ));

        newsFinded.setTitular(news.titular());
        newsFinded.setDescripcion(news.descripcion());
        newsFinded.setDescripcion_adicional(news.descripcion_adicional());
        newsFinded.setFecha(news.fecha());
        newsFinded.setImagen(news.imagen());
        //logica img..

        newsFinded.setCategoria(categoryRepository
                .findById(news.categoria().id())
                .orElseThrow(() -> new NotFoundException("Categoria no encontrada, ID: " + id)));

        if(news.evento() != null) {
            newsFinded.setEvento(eventRepository
                    .findById(news.evento().id())
                    .orElseThrow(() -> new NotFoundException("Evento no encontrado, ID: " + id)));
        }

        return news;
    }

    @Override
    public void deleteNews(Long id) {
        News newToDelete = newsRepository.findById(id).orElseThrow(() -> new NotFoundException("Evento no encontrado, ID: " + id));

        //logica img..

        newsRepository.delete(newToDelete);
    }
}
