package ar.gob.pueblogeneralbelgrano.municipalidad.model;

public enum TransparencyType {
    BOLETIN_OFICIAL("Boletín Oficial", "boletin-oficial"),
    LICITACIONES("Licitaciones Públicas", "licitaciones"),
    ORDENANZAS("Ordenanzas y Decretos", "ordenanzas"),
    PRESUPUESTO("Presupuesto Municipal", "presupuesto"),
    CONTRATOS("Contratos y Convenios", "contratos");

    private final String nombre;
    private final String slug;

    TransparencyType(String nombre, String slug) {
        this.nombre = nombre;
        this.slug = slug;
    }
}
