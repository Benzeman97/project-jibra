package com.benz.authorization.server.api.db;

public enum ERole {

    ROLE_USER(101, "USER"),
    ROLE_MODERATOR(102, "MODERATOR"),
    ROLE_ADMIN(103, "ADMIN");

    private final int value;
    private final String role;

    ERole(int value, String role) {
        this.value = value;
        this.role = role;
    }

    public int value() {
        return value;
    }

    public String role() {
        return role;
    }
}
