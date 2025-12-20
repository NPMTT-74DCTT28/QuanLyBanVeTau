/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.enums;

/**
 *
 * @author qphwn
 */
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
