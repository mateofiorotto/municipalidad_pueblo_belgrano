package ar.gob.pueblogeneralbelgrano.municipalidad.service.transparency;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.news.NewsResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.transparency.TransparencyRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.transparency.TransparencyResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.TransparencyType;
import org.springframework.data.web.PagedModel;

import java.util.List;

public interface ITransparencyService {
    /**
     * Devolver transparencias paginadas
     *
     * @param page
     * @param size
     * @return transparencias paginadas
     */
    public PagedModel<TransparencyResponseDTO> getPaginatedTransparencies(int page, int size);

    /**
     * Devolver las transparencias por tipo
     */
    //public PagedModel<TransparencyResponseDTO> getTransparenciesByType(int page, int size, String type);

    /**
     * Devolver lista de tipos
     */
    public List<TransparencyType> transparencyTypesList();

    /**
     * Devolver una transparencia por su ID
     * @param id
     * @return transparencia por id
     */
    public TransparencyResponseDTO getTransparencyById(Long id);

    /**
     * Devolver las transparencias por tipo
     * @param page
     * @param size
     * @param type
     * @return transparencias paginadas filtradas por tipo
     */
    public PagedModel<TransparencyResponseDTO> getTransparenciesByType(int page, int size, String type);

    /**
     * Guardar transparencia en la base de datos
     * @param transparency
     * @return la transparencia guardada
     */
    public TransparencyRequestDTO saveTransparency(TransparencyRequestDTO transparency);

    /**
     * Actualizar transparencia en la base de datos
     * @param transparency
     * @param id
     * @return la transparencia actualizada
     */
    public TransparencyRequestDTO updateTransparency(TransparencyRequestDTO transparency, Long id);

    /**
     * Elimina una transparencia en la base de datos
     * @param id
     */
    public void deleteTransparency(Long id);
}
