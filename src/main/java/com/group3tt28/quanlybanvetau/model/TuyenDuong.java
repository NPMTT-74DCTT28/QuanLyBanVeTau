package com.group3tt28.quanlybanvetau.model;

public class TuyenDuong {

    private int id;
    private String maTuyen;
    private String tenTuyen;
    private int idGaDi;
    private int idGaDen;
    private double khoangCachKm;
    private double giaCoBan;

    public TuyenDuong() {
    }

    public TuyenDuong(String maTuyen, String tenTuyen, int idGaDi, int idGaDen, double khoangCachKm, double giaCoBan) {
        this.maTuyen = maTuyen;
        this.tenTuyen = tenTuyen;
        this.idGaDi = idGaDi;
        this.idGaDen = idGaDen;
        this.khoangCachKm = khoangCachKm;
        this.giaCoBan = giaCoBan;
    }

    public TuyenDuong(int id, String maTuyen, String tenTuyen, int idGaDi, int idGaDen, double khoangCachKm, double giaCoBan) {
        this(maTuyen, tenTuyen, idGaDi, idGaDen, khoangCachKm, giaCoBan);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaTuyen() {
        return maTuyen;
    }

    public void setMaTuyen(String maTuyen) {
        this.maTuyen = maTuyen;
    }

    public String getTenTuyen() {
        return tenTuyen;
    }

    public void setTenTuyen(String tenTuyen) {
        this.tenTuyen = tenTuyen;
    }

    public int getIdGaDi() {
        return idGaDi;
    }

    public void setIdGaDi(int idGaDi) {
        this.idGaDi = idGaDi;
    }

    public int getIdGaDen() {
        return idGaDen;
    }

    public void setIdGaDen(int idGaDen) {
        this.idGaDen = idGaDen;
    }

    public double getKhoangCachKm() {
        return khoangCachKm;
    }

    public void setKhoangCachKm(double khoangCachKm) {
        this.khoangCachKm = khoangCachKm;
    }

    public double getGiaCoBan() {
        return giaCoBan;
    }

    public void setGiaCoBan(double giaCoBan) {
        this.giaCoBan = giaCoBan;
    }

    @Override
    public String toString() {
        return tenTuyen;
    }
}
