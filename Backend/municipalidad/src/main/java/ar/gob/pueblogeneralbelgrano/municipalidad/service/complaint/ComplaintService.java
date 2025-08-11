package ar.gob.pueblogeneralbelgrano.municipalidad.service.complaint;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintUpdateDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint.ComplaintResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.BadRequestException;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.NotFoundException;
import ar.gob.pueblogeneralbelgrano.municipalidad.mapper.IAreaMapper;
import ar.gob.pueblogeneralbelgrano.municipalidad.mapper.IComplaintMapper;
import ar.gob.pueblogeneralbelgrano.municipalidad.mapper.IComplaintMapper;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Complaint;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Complaint;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.IAreaRepository;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.IComplaintRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplaintService implements IComplaintService {

    private final IComplaintRepository complaintRepository;
    private final IAreaRepository areaRepository;

    public ComplaintService(IComplaintRepository complaintRepository, IAreaRepository areaRepository) {
        this.complaintRepository = complaintRepository;
        this.areaRepository = areaRepository;
    }

    @Override
    public PagedModel<ComplaintResponseDTO> getPaginatedComplaints(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Complaint> paginatedComplaints = complaintRepository.findAllRecentOpenComplaints(pageable);

        List<ComplaintResponseDTO> complaintDTOList = paginatedComplaints
                .stream()
                .map(IComplaintMapper.mapper::complaintToComplaintResponseDTO)
                .collect(Collectors.toList());

        Page<ComplaintResponseDTO> paginatedComplaintList = new PageImpl<>(
                complaintDTOList,
                pageable,
                paginatedComplaints.getTotalElements()
        );

        return new PagedModel<>(paginatedComplaintList);
    }

    @Override
    public PagedModel<ComplaintResponseDTO> getPaginatedComplaintsByArea(int page, int size, Long area_id) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Complaint> paginatedComplaintsByArea = complaintRepository.findComplaintsByArea(pageable, area_id);

        List<ComplaintResponseDTO> complaintDTOList = paginatedComplaintsByArea
                .stream()
                .map(IComplaintMapper.mapper::complaintToComplaintResponseDTO)
                .collect(Collectors.toList());

        Page<ComplaintResponseDTO> paginatedComplaintList = new PageImpl<>(
                complaintDTOList,
                pageable,
                paginatedComplaintsByArea.getTotalElements()
        );

        return new PagedModel<>(paginatedComplaintList);
    }

    @Override
    public ComplaintResponseDTO getComplaintById(Long id) {

        Complaint complaint = complaintRepository.findValidComplaintById(id).orElseThrow(() -> new NotFoundException("Reclamo no encontrado o cerrado hace más de 7 dias, ID: " + id));

        return IComplaintMapper.mapper.complaintToComplaintResponseDTO(complaint);
    }

    @Override
    public ComplaintRequestDTO saveComplaint(ComplaintRequestDTO complaint) {

        Complaint complaintToSave = IComplaintMapper.mapper.complaintRequestDTOToComplaint(complaint);

        complaintToSave.setCerrado(Boolean.FALSE);
        complaintToSave.setFecha_reclamo(LocalDate.now());
        complaintToSave.setFecha_cerrado(null);
        complaintToSave.setComentario("");
        complaintToSave.setArea(null);

        //implementar rate limiter
        complaintRepository.save(complaintToSave);

        return complaint;
    }

    // LA IMAGEN NO SE MODIFICA
    @Override
    public ComplaintUpdateDTO updateComplaint(ComplaintUpdateDTO complaint, Long id) {

        Complaint complaintFinded = complaintRepository.findValidComplaintById(id).orElseThrow(() -> new NotFoundException("Reclamo no encontrado o cerrado hace más de 7 dias, ID: " + id));

        //Si ya cerro, negar el update
        if (Boolean.TRUE.equals(complaintFinded.getCerrado())
                && complaintFinded.getFecha_cerrado() != null) {

            LocalDate fechaCerrado = complaintFinded.getFecha_cerrado();

            LocalDate limite = LocalDate.now().minusDays(7);

            if (fechaCerrado.isBefore(limite)) {
                throw new BadRequestException("No se puede modificar un reclamo cerrado hace más de 7 días.");
            }
        }

        if (complaint.comentario() != null && !complaint.comentario().isBlank()) {
            complaintFinded.setComentario(complaint.comentario());
        } else {
            complaintFinded.setComentario("");
        }

        if (complaint.area() != null) {
            complaintFinded.setArea(areaRepository.findById(complaint.area().id()).orElseThrow(() -> new NotFoundException("Area no encontrada, ID: " + complaint.area().id())));
        } else {
            complaintFinded.setArea(null);
        }

        if (complaint.cerrado() != null) {
            complaintFinded.setCerrado(complaint.cerrado());

            if (Boolean.TRUE.equals(complaint.cerrado())) {
                complaintFinded.setFecha_cerrado(LocalDate.now()); //reclamo cerrado, asignando fecha
            } else {
                complaintFinded.setFecha_cerrado(null); //reclamo que se vuelve a abrir, se desasigna la fecha
            }
        }

        complaintRepository.save(complaintFinded);

        return complaint;
    }

    @Override
    public void deleteComplaint(Long id) {

        Complaint complaintToDelete = complaintRepository.findById(id).orElseThrow(() -> new NotFoundException("Reclamo no encontrado, ID: " + id));

        //Logica para eliminar IMG

        complaintRepository.delete(complaintToDelete);
    }
}
