package ar.gob.pueblogeneralbelgrano.municipalidad.service.usersec;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.auth.AuthLoginRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.auth.AuthResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.model.UserSec;
import ar.gob.pueblogeneralbelgrano.municipalidad.repository.IUserSecRepository;
import ar.gob.pueblogeneralbelgrano.municipalidad.utils.JwtUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final IUserSecRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImp(IUserSecRepository userRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Cargar usuario por su nombre de usuario y los authorities de ese usuario
     *
     * @param username
     *
     * @return usuario y authorities
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserSec userSec = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userSec.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole()))));

        userSec.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getPermission())));

        return new User(userSec.getUsername(),
                userSec.getPassword(),
                userSec.isEnabled(),
                true,
                true,
                true,
                authorityList);
    }

    /**
     * Login con nombre de usuario y contraseña
     *
     * @param authLoginRequest
     * @return respuesta si el login esta OK o datos incorrectos (desde el metodo auth)
     */
    public AuthResponseDTO login (AuthLoginRequestDTO authLoginRequest){
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        //llamar al metodo de autenticacion
        Authentication authentication = this.authenticate (username, password);

        //creacion de jwt y response
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(username, "Login Exitoso", accessToken, true);

        return authResponseDTO;
    }

    /**
     * Autentica al usaurio
     *
     * @param username
     * @param password
     * @return usuario, authorities y contraseña
     */
    public Authentication authenticate(String username, String password) {
        UserDetails userDetails;

        //si !user
        try {
            userDetails = this.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Los datos ingresados son incorrectos");
        }

        //si !pw
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Los datos ingresados son incorrectos");
        }
        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }


}
