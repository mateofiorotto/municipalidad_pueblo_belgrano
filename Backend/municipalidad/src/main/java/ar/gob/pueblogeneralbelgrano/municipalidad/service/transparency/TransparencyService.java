package ar.gob.pueblogeneralbelgrano.municipalidad.service.transparency;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.transparency.TransparencyRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.transparency.TransparencyResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.NotFoundException;
import ar.gob.pueblogeneralbelgrano.municipalidad.mapper.ITransparencyMapper;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Transparency;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.ICategoryRepository;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.IEventRepository;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.ITransparencyRepository;
import ar.gob.pueblogeneralbelgrano.municipalidad.service.transparency.ITransparencyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransparencyService implements ITransparencyService {

    private final ITransparencyRepository transparencyRepository;

    public TransparencyService(ITransparencyRepository transparencyRepository) {
        this.transparencyRepository = transparencyRepository;
    }

    @Override
    public List<TransparencyResponseDTO> getTransparencies() {
        List<Transparency> transparencyList = transparencyRepository.findAll();

        return transparencyList
                .stream()
                .map(transparency -> ITransparencyMapper.mapper.transparencyToTransparencyResponseDTO(transparency))
                .collect(Collectors.toList());
    }

    @Override
    public TransparencyResponseDTO getTransparencyById(Long id) {
        Transparency transparency = transparencyRepository.findById(id).orElseThrow(() -> new NotFoundException("Transparencia no encontrada,  ID" + id));

        return ITransparencyMapper.mapper.transparencyToTransparencyResponseDTO(transparency);
    }

    @Override
    public TransparencyRequestDTO saveTransparency(TransparencyRequestDTO transparency) {
        Transparency transparencyToSave = ITransparencyMapper.mapper.transparencyRequestDTOToTransparency(transparency);

        //metodo pdf

        transparencyRepository.save(transparencyToSave);

        return transparency;
    }

    @Override
    public TransparencyRequestDTO updateTransparency(TransparencyRequestDTO transparency, Long id) {
        Transparency transparencyFinded = transparencyRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontro la transparencia, ID: " + id ));

        transparencyFinded.setFecha(transparency.fecha());
        transparencyFinded.setPdf(transparency.pdf());
        
        //logica pdf

        return transparency;
    }

    @Override
    public void deleteTransparency(Long id) {
        Transparency newToDelete = transparencyRepository.findById(id).orElseThrow(() -> new NotFoundException("Transparencia no encontrada, ID: " + id));

        //logica pdf

        transparencyRepository.delete(newToDelete);
    }
}
