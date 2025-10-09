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
        @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "El motivo no puede contener números ni caracteres especiales")
        String motivo,

        @NotBlank(message = "El nombre y apellido no pueden estar vacíos")
        @Size(min = 5, max = 100, message = "El nombre y apellido deben tener entre 5 y 100 caracteres")
        @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "El nombre y apellido no puede contener números ni caracteres especiales")
        String nombre_apellido,

        @NotBlank(message = "El celular no puede estar vacío")
        @Size(min = 6, max = 20, message = "El celular debe tener entre 6 y 20 caracteres")
        @Pattern(regexp = "^[0-9]+$", message = "El celular solo debe contener números")
        String celular,

        @NotBlank(message = "La dirección no puede estar vacía")
        @Size(min = 5, max = 150, message = "La dirección debe tener entre 5 y 150 caracteres")
        @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9 ]+$",
                message = "La dirección solo puede contener letras, números, espacios y tildes")
        String direccion,

        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "El email no es válido")
        @Pattern(regexp = "^[A-Za-z0-9._-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                message = "El email contiene caracteres no válidos")
        String email,

        @NotBlank(message = "La descripción no puede estar vacía")
        @Size(min = 20, max = 1000, message = "La descripción debe tener entre 20 y 1000 caracteres")
        @Pattern(regexp = "^[^<>]*$", message = "La descripción no puede contener < >")
        String descripcion,

        String captcha

) {
}
