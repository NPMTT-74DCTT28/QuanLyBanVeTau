package com.group3tt28.quanlybanvetau.model.dto;

public class DoanhSoNhanVien {

    private String hoTen;
    private double doanhSo;

    public DoanhSoNhanVien(String hoTen, double doanhSo) {
        this.hoTen = hoTen;
        this.doanhSo = doanhSo;
    }

    public String getHoTen() {
        return hoTen;
    }

    public double getDoanhSo() {
        return doanhSo;
    }
}
