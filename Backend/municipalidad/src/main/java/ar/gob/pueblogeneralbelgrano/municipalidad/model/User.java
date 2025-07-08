package ar.gob.pueblogeneralbelgrano.municipalidad.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private Long dni;
    private String foto_dni;
    private String email;
    @OneToOne
    @JoinColumn(name = "user_sec_id")
    private UserSec userSec;
}
