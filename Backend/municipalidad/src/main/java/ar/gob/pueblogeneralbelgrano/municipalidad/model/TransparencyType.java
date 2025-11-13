package ar.gob.pueblogeneralbelgrano.municipalidad.model;

public enum TransparencyType {
    ORDENANZAS("Ordenanzas", "ordenanzas"),
    DECRETOS("Decretos", "decretos");

    private final String nombre;
    private final String slug;

    TransparencyType(String nombre, String slug) {
        this.nombre = nombre;
        this.slug = slug;
    }
}
