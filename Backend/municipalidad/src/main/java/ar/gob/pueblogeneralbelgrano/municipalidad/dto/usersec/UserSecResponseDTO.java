package ar.gob.pueblogeneralbelgrano.municipalidad.dto.usersec;

import ar.gob.pueblogeneralbelgrano.municipalidad.dto.role.RoleResponseDTO;

import java.util.Set;

public record UserSecResponseDTO (
    String username,
    boolean enabled,
    boolean accountNotExpired,
    boolean accountNotLocked,
    boolean credentialNotExpired,
    Set<RoleResponseDTO> roles){
}
