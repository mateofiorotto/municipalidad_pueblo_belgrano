package ar.gob.pueblogeneralbelgrano.municipalidad.model;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Objects;

@Entity
@Table(name = "reclamos")
@SQLDelete(sql = "UPDATE roles SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Complaints {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String motivo;
    private String descripcion;
    private String imagen; //opcional
    //manytoone --> en usuario va onetomany
    //private UserSec usuario;
    private boolean deleted = Boolean.FALSE;

    public Complaints(){}


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Complaints that = (Complaints) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
