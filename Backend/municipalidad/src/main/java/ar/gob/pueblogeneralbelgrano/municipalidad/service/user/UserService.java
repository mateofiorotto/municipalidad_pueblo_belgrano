package ar.gob.pueblogeneralbelgrano.municipalidad.service.user;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.role.RoleIdDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec.UserSecUpdateDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.BadRequestException;
import ar.gob.pueblogeneralbelgrano.municipalidad.exception.NotFoundException;
import ar.gob.pueblogeneralbelgrano.municipalidad.mapper.IUserMapper;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.Role;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.UserSec;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.IRoleRepository;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.IUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    public UserService(IUserRepository userRepository, IRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserSecResponseDTO> getUsers() {
        List<UserSec> users = userRepository.findAll();

        return users.stream().map(user -> IUserMapper.mapper.userSecToUserSecGetDTO(user)).collect(Collectors.toList());
    }

    @Override
    public UserSecResponseDTO getUserById(Long id) {
        UserSec user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with ID: " + id));

        return IUserMapper.mapper.userSecToUserSecGetDTO(user);
    }

    @Override
    public UserSecRequestDTO saveUser(UserSecRequestDTO user) {

        UserSec userToSave = IUserMapper.mapper.userSecSaveDTOToUserSec(user);

        userToSave.setPassword(this.encriptPassword(user.getPassword()));

        Set<Role> roleList = new HashSet<>();
        for (RoleIdDTO r : user.getRoles()) {
            Role existingRole = roleRepository.findById(r.id()).orElseThrow(() -> new BadRequestException("Role not found"));

            roleList.add(existingRole);
        }

        userToSave.setRoles(roleList);
        UserSec savedUser = userRepository.save(userToSave);

        return IUserMapper.mapper.userSecToUserSecSaveDTO(savedUser);
    }

    @Override
    public UserSecUpdateDTO updateUser(UserSecUpdateDTO user, Long id) {
        UserSec userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));

        userToUpdate.setPassword(this.encriptPassword(user.getPassword()));
        userToUpdate.setEnabled(user.isEnabled());
        userToUpdate.setAccountNotExpired(user.isAccountNotExpired());
        userToUpdate.setAccountNotLocked(user.isAccountNotLocked());
        userToUpdate.setCredentialNotExpired(user.isCredentialNotExpired());

        Set<Role> roleList = new HashSet<>();
        for (RoleIdDTO r : user.getRoles()) {
            Role existingRole = roleRepository.findById(r.id())
                    .orElseThrow(() -> new BadRequestException("Role not found"));

            roleList.add(existingRole);
        }

        userToUpdate.setRoles(roleList);

        UserSec updatedUser = userRepository.save(userToUpdate);

        return IUserMapper.mapper.userSecToUserSecUpdateDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        UserSec userToDelete = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with ID: " + id));

        userToDelete.getRoles().clear();

        userRepository.delete(userToDelete);
    }

    @Override
    public String encriptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
