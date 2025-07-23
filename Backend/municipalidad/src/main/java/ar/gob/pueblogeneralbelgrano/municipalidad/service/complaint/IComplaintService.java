package ar.gob.pueblogeneralbelgrano.municipalidad.service.complaint;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintUpdateDTO;

import java.util.List;

public interface IComplaintService {
    /**
     * Retornar una lista de todos los complaintos
     *
     * @return lista de complaintos
     * */
    public List<ComplaintResponseDTO> getComplaints();

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
