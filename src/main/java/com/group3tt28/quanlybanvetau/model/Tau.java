package com.group3tt28.quanlybanvetau.model;

public class Tau {

    private int id;
    private String maTau;
    private String tenTau;

    public Tau() {
    }

    public Tau(String maTau, String tenTau) {
        this.maTau = maTau;
        this.tenTau = tenTau;
    }

    public Tau(int id, String maTau, String tenTau) {
        this(maTau, tenTau);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaTau() {
        return maTau;
    }

    public void setMaTau(String maTau) {
        this.maTau = maTau;
    }

    public String getTenTau() {
        return tenTau;
    }

    public void setTenTau(String tenTau) {
        this.tenTau = tenTau;
    }

    @Override
    public String toString() {
        return tenTau;
    }
}
