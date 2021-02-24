package com.benz.security.web.api.db;

public enum EPermission {

    CAN_CREATE(10,"CAN_CREATE"),CAN_UPDATE(20,"CAN_UPDATE"),CAN_DELETE(30,"CAN_DELETE")
    ,CAN_READ(40,"CAN_READ");

    private int id;
    private String name;

    EPermission(int id,String name)
    {
        this.id=id;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
