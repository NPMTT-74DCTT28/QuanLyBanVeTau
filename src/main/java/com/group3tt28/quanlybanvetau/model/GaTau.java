package com.group3tt28.quanlybanvetau.model;

public class GaTau {

    private int id;
    private String maGa;
    private String tenGa;
    private String diaChi;
    private String thanhPho;

    public GaTau() {
    }

    public GaTau(String maGa, String tenGa, String diaChi, String thanhPho) {
        this.maGa = maGa;
        this.tenGa = tenGa;
        this.diaChi = diaChi;
        this.thanhPho = thanhPho;
    }

    public GaTau(int id, String maGa, String tenGa, String diaChi, String thanhPho) {
        this(maGa, tenGa, diaChi, thanhPho);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaGa() {
        return maGa;
    }

    public void setMaGa(String maGa) {
        this.maGa = maGa;
    }

    public String getTenGa() {
        return tenGa;
    }

    public void setTenGa(String tenGa) {
        this.tenGa = tenGa;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getThanhPho() {
        return thanhPho;
    }

    public void setThanhPho(String thanhPho) {
        this.thanhPho = thanhPho;
    }
    @Override
    public String toString() {
        return this.maGa + " - " + this.tenGa;
    }
}
