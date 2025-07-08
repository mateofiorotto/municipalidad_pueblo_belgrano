package ar.gob.pueblogeneralbelgrano.municipalidad.model;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "eventos")
@SQLDelete(sql = "UPDATE roles SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titular;
    private Date fecha;
    private String imagen;
    private String descripcion;
    private String descripcion_adicional; //opcional
    private boolean deleted = Boolean.FALSE;

    public Event(){}

    public Event(Long id, String titular, Date fecha, String imagen, String descripcion, String descripcion_adicional) {
        this.id = id;
        this.titular = titular;
        this.fecha = fecha;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.descripcion_adicional = descripcion_adicional;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion_adicional() {
        return descripcion_adicional;
    }

    public void setDescripcion_adicional(String descripcion_adicional) {
        this.descripcion_adicional = descripcion_adicional;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
