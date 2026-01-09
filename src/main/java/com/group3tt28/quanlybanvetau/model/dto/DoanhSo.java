package com.group3tt28.quanlybanvetau.model.dto;

public class DoanhSo {

    private String maNhanVien;
    private String hoTen;
    private int soVeBan;
    private double doanhSo;

    public DoanhSo(String maNhanVien, String hoTen, int soVeBan, double doanhSo) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.soVeBan = soVeBan;
        this.doanhSo = doanhSo;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public int getSoVeBan() {
        return soVeBan;
    }

    public double getDoanhSo() {
        return doanhSo;
    }
}
