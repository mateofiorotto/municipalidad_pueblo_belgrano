package ar.gob.pueblogeneralbelgrano.municipalidad.model;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "noticias")
@SQLDelete(sql = "UPDATE noticias SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titular;
    @Column(nullable = false)
    private String subtitulo;
    @Column(nullable = false)
    private LocalDate fecha;
    @Column(nullable = false)
    private String imagen;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;
    @Column(columnDefinition = "TEXT")
    private String descripcion_adicional; //opcional
    //relacion con categoria
    //manytoone
    @ManyToOne
    @JoinColumn(name="categoria_id", nullable = false)
    private Category categoria;
    //relacion con evento (opcional)
    //manytoone
    @ManyToOne
    @JoinColumn(name="evento_id", nullable = true)
    private Event evento;
    private boolean deleted = Boolean.FALSE;

    public News(){}

    public News(Long id, String titular, String subtitulo, LocalDate fecha, String imagen, String descripcion, String descripcion_adicional, Category categoria, Event evento) {
        this.id = id;
        this.titular = titular;
        this.subtitulo = subtitulo;
        this.fecha = fecha;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.descripcion_adicional = descripcion_adicional;
        this.categoria = categoria;
        this.evento = evento;
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

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
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

    public Category getCategoria() {
        return categoria;
    }

    public void setCategoria(Category categoria) {
        this.categoria = categoria;
    }

    public Event getEvento() {
        return evento;
    }

    public void setEvento(Event evento) {
        this.evento = evento;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(id, news.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
