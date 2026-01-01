package com.group3tt28.quanlybanvetau.model.dto;

public class DoanhThuNgay {

    private String ngay;
    private int soVeBan;
    private double doanhThu;

    public DoanhThuNgay(String ngay, int soVeBan, double doanhThu) {
        this.ngay = ngay;
        this.soVeBan = soVeBan;
        this.doanhThu = doanhThu;
    }

    public String getNgay() {
        return ngay;
    }

    public int getSoVeBan() {
        return soVeBan;
    }

    public double getDoanhThu() {
        return doanhThu;
    }
}
