package com.group3tt28.quanlybanvetau.model;

public class LoaiToa {

    private int id;
    private String tenLoai;
    private double heSoGia;

    public LoaiToa() {
    }

    public LoaiToa(String tenLoai, double heSoGia) {
        this.tenLoai = tenLoai;
        this.heSoGia = heSoGia;
    }

    public LoaiToa(int id, String tenLoai, double heSoGia) {
        this(tenLoai, heSoGia);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public double getHeSoGia() {
        return heSoGia;
    }

    public void setHeSoGia(double heSoGia) {
        this.heSoGia = heSoGia;
    }

    @Override
    public String toString() {
        return tenLoai;
    }
}
