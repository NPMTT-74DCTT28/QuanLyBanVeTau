package com.group3tt28.quanlybanvetau.model.dto;

public class TyLeLapDay {

    private String maLichTrinh;
    private String tenTau;
    private double tyLeLapDay;

    public TyLeLapDay(String maLichTrinh, String tenTau, double tyLeLapDay) {
        this.maLichTrinh = maLichTrinh;
        this.tenTau = tenTau;
        this.tyLeLapDay = tyLeLapDay;
    }

    public String getMaLichTrinh() {
        return maLichTrinh;
    }

    public String getTenTau() {
        return tenTau;
    }

    public double getTyLeLapDay() {
        return tyLeLapDay;
    }
}
