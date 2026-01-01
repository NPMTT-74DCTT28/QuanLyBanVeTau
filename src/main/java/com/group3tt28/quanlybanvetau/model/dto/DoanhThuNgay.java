package com.group3tt28.quanlybanvetau.model.dto;

public class DoanhThuNgay {

    private String ngay;
    private double doanhThu;
    private int soVeBan;

    public DoanhThuNgay(String ngay, double doanhThu, int soVeBan) {
        this.ngay = ngay;
        this.doanhThu = doanhThu;
        this.soVeBan = soVeBan;
    }

    public String getNgay() {
        return ngay;
    }

    public double getDoanhThu() {
        return doanhThu;
    }

    public int getSoVeBan() {
        return soVeBan;
    }
}
