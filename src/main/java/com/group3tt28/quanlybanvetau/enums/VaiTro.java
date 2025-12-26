package com.group3tt28.quanlybanvetau.enums;

public enum VaiTro {
    ADMIN("Quản trị viên"),
    NHAN_VIEN("Nhân viên");

    private final String label;

    private VaiTro(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
