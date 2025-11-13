package com.gft.envio_rapido_api.enums;

public enum UsuarioPapel {
    ADMIN("admin"), USER("user");
    private String role;

    UsuarioPapel(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
