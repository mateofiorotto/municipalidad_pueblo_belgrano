package ar.gob.pueblogeneralbelgrano.municipalidad.service.transparency;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.transparency.TransparencyRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.transparency.TransparencyResponseDTO;

import java.util.List;

public interface ITransparencyService {
    /**
     * Devolver lista de transparencias
     * @return lista de transparencias
     */
    public List<TransparencyResponseDTO> getTransparencies();

    /**
     * Devolver una transparencia por su ID
     * @param id
     * @return transparencia por id
     */
    public TransparencyResponseDTO getTransparencyById(Long id);

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
