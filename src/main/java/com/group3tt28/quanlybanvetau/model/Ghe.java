package com.group3tt28.quanlybanvetau.model;

public class Ghe {

    private int id;
    private String soGhe;
    private int idToaTau;

    public Ghe() {
    }

    public Ghe(String soGhe, int idToaTau) {
        this.soGhe = soGhe;
        this.idToaTau = idToaTau;
    }

    public Ghe(int id, String soGhe, int idToaTau) {
        this(soGhe, idToaTau);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSoGhe() {
        return soGhe;
    }

    public void setSoGhe(String soGhe) {
        this.soGhe = soGhe;
    }

    public int getIdToaTau() {
        return idToaTau;
    }

    public void setIdToaTau(int idToaTau) {
        this.idToaTau = idToaTau;
    }
}
