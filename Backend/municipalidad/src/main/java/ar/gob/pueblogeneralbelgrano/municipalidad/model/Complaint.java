package ar.gob.pueblogeneralbelgrano.municipalidad.model;

import ar.gob.pueblogeneralbelgrano.municipalidad.model.Area;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "reclamos")
@SQLDelete(sql = "UPDATE reclamos SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String motivo;
    @Column(nullable = false)
    private String nombre_apellido;
    @Column(nullable = false)
    private String celular;
    @Column(nullable = false)
    private String direccion;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;
    private String imagen; //opcional
    private LocalDate fecha_reclamo; //solo back
    private LocalDate fecha_cerrado; //solo back
    private Boolean cerrado = Boolean.FALSE;
    private boolean deleted = Boolean.FALSE;
    private String comentario; //Solo backend en la web
    private String ip; //automatico --> guarda ip del creador del reclamo
    @ManyToOne
    @JoinColumn(name="area_id", nullable = true)
    private Area area; //Solo backend

    public Complaint(){}

    public Complaint(Long id, String motivo, String nombre_apellido, String celular, String direccion, String email, String descripcion, String imagen, String comentario, Area area, LocalDate fecha_reclamo, LocalDate fecha_cerrado, Boolean cerrado, String ip) {
        this.id = id;
        this.motivo = motivo;
        this.nombre_apellido = nombre_apellido;
        this.celular = celular;
        this.direccion = direccion;
        this.email = email;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.comentario = comentario;
        this.area = area;
        this.fecha_reclamo = fecha_reclamo;
        this.fecha_cerrado = fecha_cerrado;
        this.cerrado = cerrado;
        this.ip = ip;
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

    public LocalDate getFecha_reclamo() {
        return fecha_reclamo;
    }

    public void setFecha_reclamo(LocalDate fecha_reclamo) {
        this.fecha_reclamo = fecha_reclamo;
    }

    public LocalDate getFecha_cerrado() {
        return fecha_cerrado;
    }

    public void setFecha_cerrado(LocalDate fecha_cerrado) {
        this.fecha_cerrado = fecha_cerrado;
    }

    public Boolean getCerrado() {
        return cerrado;
    }

    public void setCerrado(Boolean cerrado) {
        this.cerrado = cerrado;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
