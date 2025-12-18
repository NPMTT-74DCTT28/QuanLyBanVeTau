/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.model;

/**
 *
 * @author qphwn
 */
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
}
