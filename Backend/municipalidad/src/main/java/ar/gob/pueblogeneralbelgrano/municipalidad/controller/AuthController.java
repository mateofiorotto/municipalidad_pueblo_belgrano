package ar.gob.pueblogeneralbelgrano.municipalidad.controller;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.auth.AuthLoginRequestDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.dto.auth.AuthResponseDTO;
import ar.gob.pueblogeneralbelgrano.municipalidad.service.usersec.UserDetailsServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
    private final UserDetailsServiceImp userDetailsServiceImp;

    public AuthController(UserDetailsServiceImp userDetailsServiceImp) {
        this.userDetailsServiceImp = userDetailsServiceImp;
    }

    /**
     *
     * @param userRequest
     * @return Mensaje de login exitoso y token jwt
     */
    @Operation(summary = "Login",
            description = "Entrar con usuario y contraseña, devuelve un token"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sesión iniciada"),
            @ApiResponse(responseCode = "401", description = "Inicio de sesión fallido")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthLoginRequestDTO userRequest){
        return new ResponseEntity<>(this.userDetailsServiceImp.login(userRequest), HttpStatus.OK);
    }
}
