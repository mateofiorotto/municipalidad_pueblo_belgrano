package ar.gob.pueblogeneralbelgrano.municipalidad.service.complaint;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintUpdateDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.news.NewsResponseDTO;
import org.springframework.data.web.PagedModel;

import java.util.List;

public interface IComplaintService {
    /**
     * Devolver reclamos paginados
     *
     * @param page
     * @param size
     * @return reclamos paginados
     */
    public PagedModel<ComplaintResponseDTO> getPaginatedComplaints(int page, int size);

    /**
     * Retornar un complainto especifico
     *
     * @param id
     * @return un complainto
     * */
    public ComplaintResponseDTO getComplaintById(Long id);

    /**
     * Crear un complainto
     *
     * @param complaint
     * @return complainto creado
     * */
    public ComplaintRequestDTO saveComplaint(ComplaintRequestDTO complaint);

    /**
     * Actualizar complainto por id
     *
     * @param complaint
     * @param id
     * @return complainto actualizado
     * */
    public ComplaintUpdateDTO updateComplaint(ComplaintUpdateDTO complaint, Long id);

    /**
     * Borrar complainto
     * @param id
     * */
    public void deleteComplaint(Long id);

}
