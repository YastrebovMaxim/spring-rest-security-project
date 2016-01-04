package ru.myastrebov.core.security;

/**
 * @author Maxim
 */
public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    ANONYMOUS("anonymous");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
