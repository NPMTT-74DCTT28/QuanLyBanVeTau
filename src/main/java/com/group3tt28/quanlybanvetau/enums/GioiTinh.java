package com.group3tt28.quanlybanvetau.enums;

public enum GioiTinh {
    NAM("Nam"),
    NU("Nữ"),
    KHAC("Khác"),;

    private String label;

    GioiTinh(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
