package ar.gob.pueblogeneralbelgrano.municipalidad.dto.complaint;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ComplaintRequestDTO(
        @NotBlank(message = "El motivo no puede estar vacío")
        @Size(min = 5, max = 100, message = "El motivo debe tener entre 5 y 100 caracteres")
        String motivo,

        @NotBlank(message = "El nombre y apellido no pueden estar vacíos")
        @Size(min = 5, max = 100, message = "El nombre y apellido deben tener entre 5 y 100 caracteres")
        String nombre_apellido,

        @NotBlank(message = "El celular no puede estar vacio")
        @Pattern(regexp = "^\\+54\\d{10}$", message = "El número debe comenzar con +54 y tener 10 dígitos después")
        String celular,

        @NotBlank(message = "La dirección no puede estar vacía")
        @Size(min = 5, max = 150, message = "La dirección debe tener entre 5 y 150 caracteres")
        String direccion,

        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "El email no es válido")
        String email,

        @NotBlank(message = "La descripción no puede estar vacía")
        @Size(min = 20, max = 1000, message = "La descripción debe tener entre 20 y 1000 caracteres")
        String descripcion,

        //opcional
        String imagen,

        //Se controlan desde el dashboard admin
        String comentario,
        String area,

        boolean cerrado) {
}
