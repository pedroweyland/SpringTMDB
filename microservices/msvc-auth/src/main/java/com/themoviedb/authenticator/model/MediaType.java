package com.themoviedb.authenticator.model;

public enum MediaType {
    MOVIE("M"),
    SERIE("S");

    private final String descripcion;

    MediaType(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static MediaType fromString(String text) {
        for (MediaType tipo : MediaType.values()) {
            if (tipo.descripcion.equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("No se pudo encontrar un tipo de media con la descripcion: " + text + ", debe ser 'M' o 'S'");
    }
}
