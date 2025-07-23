package ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.role.RoleIdDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

public class UserSecUpdateDTO {
    //Username should not be editable
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

    public UserSecUpdateDTO(){}

    public UserSecUpdateDTO(String password, boolean enabled, boolean accountNotExpired, boolean accountNotLocked, boolean credentialNotExpired, Set<RoleIdDTO> roles) {
        this.password = password;
        this.enabled = enabled;
        this.accountNotExpired = accountNotExpired;
        this.accountNotLocked = accountNotLocked;
        this.credentialNotExpired = credentialNotExpired;
        this.roles = roles;
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
