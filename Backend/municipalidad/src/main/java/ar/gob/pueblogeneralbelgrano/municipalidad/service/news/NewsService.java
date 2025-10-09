package ar.gob.pueblogeneralbelgrano.municipalidad.service.news;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.category.CategoryResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.news.NewsRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.news.NewsResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.NotFoundException;
import ar.gob.pueblogeneralbelgrano.municipalidad.mapper.ICategoryMapper;
import ar.gob.pueblogeneralbelgrano.municipalidad.mapper.INewsMapper;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Category;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.News;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.ICategoryRepository;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.IEventRepository;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.INewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
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
    public PagedModel<NewsResponseDTO> getPaginatedNews(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<News> paginatedNews = newsRepository.findAllByOrderByFechaDesc(pageable);

        List<NewsResponseDTO> newsDTOList = paginatedNews
                .stream()
                .map(INewsMapper.mapper::newsToNewsResponseDTO)
                .collect(Collectors.toList());

        //crear un nuevo objeto page y le pasamos la lista de categorias transformadas y la info de paginas
        Page<NewsResponseDTO> paginatedNewsList = new PageImpl<>(
                newsDTOList,
                pageable,
                paginatedNews.getTotalElements()
        );

        return new PagedModel<>(paginatedNewsList);
    }

    @Override
    public List<NewsResponseDTO> getLastThreeNews() {
        return newsRepository.findLastThreeNews()
                .stream()
                .map(news -> INewsMapper.mapper.newsToNewsResponseDTO(news))
                .collect(Collectors.toList());
    }

    @Override
    public NewsResponseDTO getNewsById(Long id) {
        News news = newsRepository.findById(id).orElseThrow(() -> new NotFoundException("Noticia no encontrada, ID: " + id));

        return INewsMapper.mapper.newsToNewsResponseDTO(news);
    }

    @Override
    public NewsRequestDTO saveNews(NewsRequestDTO news) {
        News newsToSave = INewsMapper.mapper.newsRequestDTOToNews(news);

        categoryRepository
                .findById(news.categoria().id())
                .orElseThrow(() -> new NotFoundException("Categoria no encontrada, ID: " + news.categoria().id()));

        if(news.evento() != null) {
            eventRepository
                    .findById(news.evento().id())
                    .orElseThrow(() -> new NotFoundException("Evento no encontrado, ID: " + news.evento().id()));
        }

        newsRepository.save(newsToSave);

        return news;
    }

    @Override
    public NewsRequestDTO updateNews(NewsRequestDTO news, Long id) {
        News newsFinded = newsRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontro la noticia, ID: " + id ));

        newsFinded.setTitular(news.titular());
        newsFinded.setSubtitulo(news.subtitulo());
        newsFinded.setDescripcion(news.descripcion());

        if(news.descripcion_adicional() != null && !news.descripcion_adicional().isBlank()) {
            newsFinded.setDescripcion_adicional(news.descripcion_adicional());
        } else {
            newsFinded.setDescripcion_adicional("");
        }

        newsFinded.setFecha(news.fecha());
        newsFinded.setImagen(news.imagen());

        newsFinded.setCategoria(categoryRepository
                .findById(news.categoria().id())
                .orElseThrow(() -> new NotFoundException("Categoria no encontrada, ID: " + news.categoria().id())));

        if(news.evento() != null) {
            newsFinded.setEvento(eventRepository
                    .findById(news.evento().id())
                    .orElseThrow(() -> new NotFoundException("Evento no encontrado, ID: " + news.evento().id())));
        } else {
            newsFinded.setEvento(null);
        }

        newsRepository.save(newsFinded);

        return news;
    }

    @Override
    public void deleteNews(Long id) {
        News newToDelete = newsRepository.findById(id).orElseThrow(() -> new NotFoundException("Noticia no encontrada, ID: " + id));

        newsRepository.delete(newToDelete);
    }

    @Override
    public PagedModel<NewsResponseDTO> getNewsByCategoryAndPaginated(int page, int size, Long category_id) {

        categoryRepository.findById(category_id).orElseThrow(() -> new NotFoundException("La categoría con esa ID no existe"));

        Pageable pageable = PageRequest.of(page, size);

        Page<News> categorizedAndPaginatedNews = newsRepository.findAllByCategoriaIdOrderByFechaDesc(category_id, pageable);

        List<NewsResponseDTO> newsDTOList = categorizedAndPaginatedNews
                .stream()
                .map(INewsMapper.mapper::newsToNewsResponseDTO)
                .collect(Collectors.toList());

        //crear un nuevo objeto page y le pasamos la lista de categorias transformadas y la info de paginas
        Page<NewsResponseDTO> categorizedAndPaginatedNewsList = new PageImpl<>(
                newsDTOList,
                pageable,
                categorizedAndPaginatedNews.getTotalElements()
        );

        return new PagedModel<>(categorizedAndPaginatedNewsList);
    }
}
