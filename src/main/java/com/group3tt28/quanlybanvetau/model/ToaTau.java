package com.group3tt28.quanlybanvetau.model;

public class ToaTau {

    private int id;
    private String maToa;
    private int idTau;
    private int idLoaiToa;

    public ToaTau() {
    }

    public ToaTau(String maToa, int idTau, int idLoaiToa) {
        this.maToa = maToa;
        this.idTau = idTau;
        this.idLoaiToa = idLoaiToa;
    }

    public ToaTau(int id, String maToa, int idTau, int idLoaiToa) {
        this(maToa, idTau, idLoaiToa);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaToa() {
        return maToa;
    }

    public void setMaToa(String maToa) {
        this.maToa = maToa;
    }

    public int getIdTau() {
        return idTau;
    }

    public void setIdTau(int idTau) {
        this.idTau = idTau;
    }

    public int getIdLoaiToa() {
        return idLoaiToa;
    }

    public void setIdLoaiToa(int idLoaiToa) {
        this.idLoaiToa = idLoaiToa;
    }

    @Override
    public String toString() {
        return id + " - " + maToa;
    }
}
