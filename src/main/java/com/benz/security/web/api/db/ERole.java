package com.benz.security.web.api.db;

public enum ERole {

    ROLE_USER(101,"ROLE_USER"),
    ROLE_ADMIN(102,"ROLE_ADMIN"),
    ROLE_MODERATOR(103,"ROLE_MODERATOR");

    private final int id;
    private final String name;

    ERole(int id,String name)
    {
        this.id=id;
        this.name=name;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
}
