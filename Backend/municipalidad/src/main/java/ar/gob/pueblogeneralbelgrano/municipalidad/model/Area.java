package ar.gob.pueblogeneralbelgrano.municipalidad.model;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "areas")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nombre;
    @OneToMany(mappedBy = "area")
    private Set<Complaint> reclamos;

    public Area(){}

    public Area(Long id, String nombre, Set<Complaint> reclamos) {
        this.id = id;
        this.nombre = nombre;
        this.reclamos = reclamos;
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

    public Set<Complaint> getReclamos() {
        return reclamos;
    }

    public void setReclamos(Set<Complaint> reclamos) {
        this.reclamos = reclamos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return Objects.equals(id, area.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
