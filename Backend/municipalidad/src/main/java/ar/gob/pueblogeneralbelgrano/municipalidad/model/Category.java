package ar.gob.pueblogeneralbelgrano.municipalidad.model;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "categorias")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nombre;
    @OneToMany(mappedBy = "categoria")
    private Set<News> noticias;

    public Category(){}

    public Category(Long id, String nombre, Set<News> noticias) {
        this.id = id;
        this.nombre = nombre;
        this.noticias = noticias;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<News> getNoticias() {
        return noticias;
    }

    public void setNoticias(Set<News> noticias) {
        this.noticias = noticias;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
