package ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.role.RoleIdDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

public class UserSecRequestDTO {
    @NotBlank(message = "El nombre de usuario es requerido")
    @Size(min = 3, max = 20, message = "El nombre de usuario debe estar entre 3 y 20 caracteres")
    private String username;
    @NotBlank(message = "La contraseña es requerida")
    private String password;
    @NotNull(message = "El campo 'enabled' no puede ser nulo")
    private boolean enabled = true;
    @NotNull(message = "El campo 'accountNotExpired' no puede ser nulo")
    private boolean accountNotExpired = true;
    @NotNull(message = "El campo 'accountNotLocked' no puede ser nulo")
    private boolean accountNotLocked = true;
    @NotNull(message = "El campo 'credentialNotExpired' no puede ser nulo")
    private boolean credentialNotExpired = true;
    @NotEmpty(message = "Al menos un rol debe estar asignado")
    private Set<RoleIdDTO> roles = new HashSet<>();

    public UserSecRequestDTO(){}

    public UserSecRequestDTO(String username, String password, boolean enabled, boolean accountNotExpired, boolean accountNotLocked, boolean credentialNotExpired, Set<RoleIdDTO> roles) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNotExpired = accountNotExpired;
        this.accountNotLocked = accountNotLocked;
        this.credentialNotExpired = credentialNotExpired;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isAccountNotExpired() {
        return accountNotExpired;
    }

    public boolean isAccountNotLocked() {
        return accountNotLocked;
    }

    public boolean isCredentialNotExpired() {
        return credentialNotExpired;
    }

    public Set<RoleIdDTO> getRoles() {
        return roles;
    }
}
