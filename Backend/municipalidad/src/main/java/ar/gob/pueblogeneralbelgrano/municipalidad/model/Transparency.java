package ar.gob.pueblogeneralbelgrano.municipalidad.model;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "transparencias")
@SQLDelete(sql = "UPDATE transparencias SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
public class Transparency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private LocalDate fecha;
    @Column(nullable = false)
    private String pdf; //referencia a un archivo pdf
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransparencyType tipo;
    private boolean deleted = Boolean.FALSE;

    public Transparency(){}

    public Transparency(Long id, String titulo, LocalDate fecha, String pdf, TransparencyType tipo) {
        this.id = id;
        this.titulo = titulo;
        this.fecha = fecha;
        this.pdf = pdf;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public TransparencyType getTipo() {
        return tipo;
    }

    public void setTipo(TransparencyType tipo) {
        this.tipo = tipo;
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
        Transparency that = (Transparency) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
