package ar.gob.pueblogeneralbelgrano.municipalidad.mapper;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintUpdateDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Complaint;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IComplaintMapper {

    IComplaintMapper mapper = Mappers.getMapper(IComplaintMapper.class);

    ComplaintResponseDTO complaintToComplaintResponseDTO(Complaint complaint);
    Complaint complaintResponseDTOToComplaint(ComplaintResponseDTO complaintResponseDTO);

    ComplaintRequestDTO complaintToComplaintRequestDTO(Complaint complaint);
    Complaint complaintRequestDTOToComplaint(ComplaintRequestDTO complaintRequestDTO);

    ComplaintUpdateDTO complaintToComplaintUpdateDTO(Complaint complaint);
}
