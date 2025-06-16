package com.themoviedb.authenticator.model;

public enum Role {
    USER("U"),
    ADMIN("A");

    private final String descripcion;

    Role(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static Role fromString(String text) {
        for (Role tipo : Role.values()) {
            if (tipo.descripcion.equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("No se pudo encontrar un tipo de media con la descripcion: " + text + ", debe ser 'M' o 'S'");
    }
}
