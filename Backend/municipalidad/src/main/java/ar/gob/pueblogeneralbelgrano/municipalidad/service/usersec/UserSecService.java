package ar.gob.pueblogeneralbelgrano.municipalidad.service.usersec;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.role.RoleIdDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.BadRequestException;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.NotFoundException;
import ar.gob.pueblogeneralbelgrano.municipalidad.mapper.IUserSecMapper;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Role;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.UserSec;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.IRoleRepository;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.IUserSecRepository;
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
    public List<UserSecResponseDTO> getUsers() {
        List<UserSec> users = userRepository.findAll();

        return users.stream().map(user -> IUserSecMapper.mapper.userSecToUserSecResponseDTO(user)).collect(Collectors.toList());
    }

    @Override
    public UserSecResponseDTO getUserById(Long id) {
        UserSec user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado, ID: " + id));

        return IUserSecMapper.mapper.userSecToUserSecResponseDTO(user);
    }

    @Override
    public UserSecRequestDTO saveUser(UserSecRequestDTO user) {

        UserSec userToSave = IUserSecMapper.mapper.userSecRequestDTOToUserSec(user);

        if (user.password().length() < 8) {
            throw new BadRequestException("La contraseña debe al menos tener 8 caracteres");
        } else {
            userToSave.setPassword(this.encriptPassword(user.password()));
        }

        Set<Role> roleList = new HashSet<>();
        for (RoleIdDTO r : user.roles()) {
            Role existingRole = roleRepository.findById(r.id()).orElseThrow(() -> new NotFoundException("Rol no encontrado, ID: " + r.id()));

            roleList.add(existingRole);
        }

        userToSave.setRoles(roleList);
        UserSec savedUser = userRepository.save(userToSave);

        return IUserSecMapper.mapper.userSecToUserSecRequestDTO(savedUser);
    }

    @Override
    public UserSecRequestDTO updateUser(UserSecRequestDTO user, Long id) {
        UserSec userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado, ID: " + id));

        if (user.password().length() < 8) {
            throw new BadRequestException("La contraseña debe al menos tener 8 caracteres");
        } else {
            userToUpdate.setPassword(this.encriptPassword(user.password()));
        }

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
