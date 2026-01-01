package com.group3tt28.quanlybanvetau.model.dto;

public class DoanhThuTuyen {

    private String tenTuyen;
    private double doanhThu;

    public DoanhThuTuyen(String tenTuyen, double doanhThu) {
        this.tenTuyen = tenTuyen;
        this.doanhThu = doanhThu;
    }

    public String getTenTuyen() {
        return tenTuyen;
    }

    public double getDoanhThu() {
        return doanhThu;
    }
}
