package com.themoviedb.authenticator.model;

public enum ListType {

    FAVORITE("F"),
    WATCHLIST("W");

    private final String descripcion;

    ListType(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static ListType fromString(String text) {
        for (ListType tipo : ListType.values()) {
            if (tipo.descripcion.equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("No se pudo encontrar un Tipo de lista con la descripcion: " + text + ", debe ser 'F' o 'W'");
    }
}
