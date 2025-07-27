package ar.gob.pueblogeneralbelgrano.municipalidad.service.area;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.area.AreaRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.area.AreaResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.ConflictException;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.NotFoundException;
import ar.gob.pueblogeneralbelgrano.municipalidad.mapper.IAreaMapper;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Area;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.IAreaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AreaService implements IAreaService {

    private final IAreaRepository areaRepository;

    public AreaService(IAreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Override
    public PagedModel<AreaResponseDTO> getPaginatedAreas(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Area> paginatedAreas = areaRepository.findAllByOrderByIdAsc(pageable);

        List<AreaResponseDTO> areaDTOList = paginatedAreas
                .stream()
                .map(IAreaMapper.mapper::areaToAreaResponseDTO)
                .collect(Collectors.toList());

        Page<AreaResponseDTO> paginatedAreasList = new PageImpl<>(
                areaDTOList,
                pageable,
                paginatedAreas.getTotalElements()
        );

        return new PagedModel<>(paginatedAreasList);
    }

    @Override
    public List<AreaResponseDTO> getAreas() {

        List<Area> areas = areaRepository.findAll();

        return areas
                .stream()
                .map(area -> IAreaMapper.mapper.areaToAreaResponseDTO(area))
                .collect(Collectors.toList());
    }

    @Override
    public AreaResponseDTO getAreaById(Long id) {
        Area area = areaRepository.findById(id).orElseThrow(() -> new NotFoundException("Area no encontrada, ID: " + id));

        return IAreaMapper.mapper.areaToAreaResponseDTO(area);
    }

    @Override
    public AreaRequestDTO saveArea(AreaRequestDTO area) {

        if (areaRepository.existsByNombre(area.nombre())) {
            throw new ConflictException("El area con el nombre " + area.nombre() + " ya existe");
        }

        Area areaToSave = IAreaMapper.mapper.areaRequestDTOToArea(area);

        areaRepository.save(areaToSave);

        return area;
    }

    @Override
    public AreaRequestDTO updateArea(AreaRequestDTO area, Long id) {
        Area areaToUpdate = areaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Area no encontrada, ID: " + id));

        if (areaRepository.existsByNombreAndIdNot(area.nombre(), id)) {
            throw new ConflictException("El area con el nombre " + area.nombre() + " ya existe");
        }

        areaToUpdate.setNombre(area.nombre());

        areaRepository.save(areaToUpdate);

        return area;
    }

    @Override
    public void deleteArea(Long id) {
        Area areaToDelete = areaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Area no encontrada, ID: " + id));

        if (areaRepository.relatedAreas(id) >= 1) {
            throw new ConflictException("No se puede eliminar el area porque se encuentra relacionada con un reclamo");
        }

        areaRepository.delete(areaToDelete);

    }
}
