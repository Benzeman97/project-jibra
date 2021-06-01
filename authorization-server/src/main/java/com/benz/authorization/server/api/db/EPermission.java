package com.benz.authorization.server.api.db;

public enum EPermission {

    CAN_CREATE(10, "CREATE"),
    CAN_READ(20, "READ"),
    CAN_UPDATE(30, "UPDATE"),
    CAN_DELETE(40, "DELETE");

    private final int value;
    private final String perm;

    EPermission(int value, String perm) {
        this.value = value;
        this.perm = perm;
    }

    public int value() {
        return value;
    }

    public String permission() {
        return perm;
    }
}
