package com.themoviedb.media.model;

public enum TipoMedia {
    MOVIE("M"),
    TVSHOW("T");

    private final String descripcion;

    TipoMedia(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static TipoMedia fromString(String text) {
        for (TipoMedia tipo : TipoMedia.values()) {
            if (tipo.descripcion.equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("No se pudo encontrar un TipoMedia con la descripcion: " + text + ", debe ser 'M' o 'T'");
    }
}
