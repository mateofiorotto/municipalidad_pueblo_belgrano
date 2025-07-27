package ar.gob.pueblogeneralbelgrano.municipalidad.service.usersec;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.role.RoleIdDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.BadRequestException;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.ConflictException;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.NotFoundException;
import ar.gob.pueblogeneralbelgrano.municipalidad.mapper.IUserSecMapper;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Role;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.UserSec;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.IRoleRepository;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.IUserSecRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserSecService implements IUserSecService {

    private final IUserSecRepository userRepository;
    private final IRoleRepository roleRepository;

    public UserSecService(IUserSecRepository userRepository, IRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public PagedModel<UserSecResponseDTO> getPaginatedUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<UserSec> paginatedUserSec = userRepository.findAllByOrderByIdAsc(pageable);

        List<UserSecResponseDTO> userSecDTOList = paginatedUserSec
                .stream()
                .map(IUserSecMapper.mapper::userSecToUserSecResponseDTO)
                .collect(Collectors.toList());

        Page<UserSecResponseDTO> paginatedUserSecList = new PageImpl<>(
                userSecDTOList,
                pageable,
                paginatedUserSec.getTotalElements()
        );

        return new PagedModel<>(paginatedUserSecList);
    }

    @Override
    public UserSecResponseDTO getUserById(Long id) {
        UserSec user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado, ID: " + id));

        return IUserSecMapper.mapper.userSecToUserSecResponseDTO(user);
    }

    @Override
    public UserSecRequestDTO saveUser(UserSecRequestDTO user) {


        if(userRepository.existsByUsername(user.username())) {
            throw new ConflictException("El usuario ingresado ya existe");
        }

        if (user.password().length() < 8) {
            throw new BadRequestException("La contraseña debe al menos tener 8 caracteres");
        }


        Set<Role> roleList = new HashSet<>();
        for (RoleIdDTO r : user.roles()) {
            Role existingRole = roleRepository.findById(r.id()).orElseThrow(() -> new NotFoundException("Rol no encontrado, ID: " + r.id()));

            roleList.add(existingRole);
        }

        UserSec userToSave = IUserSecMapper.mapper.userSecRequestDTOToUserSec(user);

        userToSave.setPassword(this.encriptPassword(user.password()));
        userToSave.setRoles(roleList);

        UserSec savedUser = userRepository.save(userToSave);

        return IUserSecMapper.mapper.userSecToUserSecRequestDTO(savedUser);
    }

    @Override
    public UserSecRequestDTO updateUser(UserSecRequestDTO user, Long id) {
        UserSec userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado, ID: " + id));

        if(userRepository.existsByUsernameAndIdNot(user.username(), userToUpdate.getId())) {
            throw new ConflictException("El nombre de usuario ingresado yaestá en uso por otra persona");
        }

        if (user.password().length() < 8) {
            throw new BadRequestException("La contraseña debe al menos tener 8 caracteres");
        }

        userToUpdate.setUsername(user.username());
        userToUpdate.setPassword(this.encriptPassword(user.password()));
        userToUpdate.setEnabled(user.enabled());

        Set<Role> roleList = new HashSet<>();
        for (RoleIdDTO r : user.roles()) {
            Role existingRole = roleRepository.findById(r.id())
                    .orElseThrow(() -> new NotFoundException("Rol no encontrado, ID: " + r.id()));

            roleList.add(existingRole);
        }

        userToUpdate.setRoles(roleList);

        UserSec updatedUser = userRepository.save(userToUpdate);

        return IUserSecMapper.mapper.userSecToUserSecRequestDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        UserSec userToDelete = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado, ID: " + id));

        userToDelete.getRoles().clear();

        userRepository.delete(userToDelete);
    }

    @Override
    public String encriptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
