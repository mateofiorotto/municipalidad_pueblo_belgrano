package ar.gob.pueblogeneralbelgrano.municipalidad.model;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "reclamos")
@SQLDelete(sql = "UPDATE roles SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String motivo;
    private String nombre_apellido;
    private String celular;
    private String direccion;
    private String email;
    private String descripcion;
    private String imagen; //opcional
    private String comentario; //Solo backend en la web
    @ManyToOne
    @JoinColumn(name="area_id", nullable = false)
    private Area area; //Solo backend
    private Date fecha_reclamo; //solo back
    private Date fecha_cerrado; //solo back
    private boolean cerrado = Boolean.FALSE;
    private boolean deleted = Boolean.FALSE;

    public Complaint(){}

    public Complaint(Long id, String motivo, String nombre_apellido, String celular, String direccion, String email, String descripcion, String imagen) {
        this.id = id;
        this.motivo = motivo;
        this.nombre_apellido = nombre_apellido;
        this.celular = celular;
        this.direccion = direccion;
        this.email = email;
        this.descripcion = descripcion;
        this.imagen = imagen;
        //solo se manejan en el back
//        this.comentario = comentario;
//        this.area = area;
//        this.cerrado = cerrado;
       // this.fecha_cerrado = fecha_cerrado;
        //this.fecha_reclamo = fecha_reclamo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getNombre_apellido() {
        return nombre_apellido;
    }

    public void setNombre_apellido(String nombre_apellido) {
        this.nombre_apellido = nombre_apellido;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public boolean isCerrado() {
        return cerrado;
    }

    public void setCerrado(boolean cerrado) {
        this.cerrado = cerrado;
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
        Complaint that = (Complaint) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
