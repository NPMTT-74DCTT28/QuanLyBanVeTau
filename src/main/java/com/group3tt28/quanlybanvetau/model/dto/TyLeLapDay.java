package com.group3tt28.quanlybanvetau.model.dto;

public class TyLeLapDay {

    private int maLichTrinh;
    private String tenTau;
    private double tyLeLapDay;

    public TyLeLapDay(int maLichTrinh, String tenTau, double tyLeLapDay) {
        this.maLichTrinh = maLichTrinh;
        this.tenTau = tenTau;
        this.tyLeLapDay = tyLeLapDay;
    }

    public int getMaLichTrinh() {
        return maLichTrinh;
    }

    public String getTenTau() {
        return tenTau;
    }

    public double getTyLeLapDay() {
        return tyLeLapDay;
    }
}
