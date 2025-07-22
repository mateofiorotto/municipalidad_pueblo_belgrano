package ar.gob.pueblogeneralbelgrano.municipalidad.service.area;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.area.AreaRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.area.AreaResponseDTO;

import java.util.List;

public interface IAreaService {
    /**
     * Devolver todas las areas
     *
     * @return lista de areas
     * */
    public List<AreaResponseDTO> getAreas();

    /**
     * Devolver una area especifica
     *
     * @param id
     * @return una area
     * */
    public AreaResponseDTO getAreaById(Long id);

    /**
     * Crear una area
     *
     * @param area
     * @return la area creada
     * */
    public AreaRequestDTO saveArea(AreaRequestDTO area);

    /**
     * Actualizar una area segun su id
     *
     * @param area
     * @param id
     * @return la area actualizada
     * */
    public AreaRequestDTO updateArea(AreaRequestDTO area, Long id);

    /**
     * Borrar una area
     * @param id
     * */
    public void deleteArea(Long id);
}
