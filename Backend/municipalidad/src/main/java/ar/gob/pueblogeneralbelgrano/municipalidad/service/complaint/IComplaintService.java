package ar.gob.pueblogeneralbelgrano.municipalidad.service.complaint;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintUpdateDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.news.NewsResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.openpdf.text.Font;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.PdfWriter;
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
    public PagedModel<ComplaintResponseDTO> getPaginatedComplaints(int page, int size, String status);

    /**
     * Devolver reclamos por area paginados
     *
     * @param page
     * @param size
     * @param area_id
     * @return reclamos por area paginados
     */
    public PagedModel<ComplaintResponseDTO> getPaginatedComplaintsByArea(int page, int size, Long area_id);

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
    public ComplaintRequestDTO saveComplaint(ComplaintRequestDTO complaint, HttpServletRequest request);

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

    /**
     * Metodo para generar el PDF de un reclamo segun su id
     * Utilizando la libreria OpenPDF. Utiliza el metodo createLabelValueParagraph
     * @param id
     */
    public byte[] generateComplaintPDF(Long id);

    /**
     * Metodo que crea parrafos reutilizables con etiquetas bold y valores normales
     * para simplificar la creacion de los mismos
     * @param label subtitulo (ej: Direccion)
     * @param value datos del reclamo (ej: Calle 123)
     * @param labelFont font usada para subtitulo
     * @param valueFont font usada para datos
     * @return
     */
    public Paragraph createLabelValueParagraph(String label, String value, Font labelFont, Font valueFont);
}
