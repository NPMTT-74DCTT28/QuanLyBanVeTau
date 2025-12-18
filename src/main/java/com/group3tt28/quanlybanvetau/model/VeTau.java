/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.model;

import java.time.LocalDateTime;

/**
 *
 * @author qphwn
 */
public class VeTau {

    private int id;
    private String maVe;
    private int idKhachHang;
    private int idLichTrinh;
    private int idGhe;
    private int idNhanVien;
    private LocalDateTime ngayDat;
    private double giaVe;
    private String trangThai;

    public VeTau() {
    }

    public VeTau(String maVe, int idKhachHang, int idLichTrinh, int idGhe, int idNhanVien, LocalDateTime ngayDat, double giaVe, String trangThai) {
        this.maVe = maVe;
        this.idKhachHang = idKhachHang;
        this.idLichTrinh = idLichTrinh;
        this.idGhe = idGhe;
        this.idNhanVien = idNhanVien;
        this.ngayDat = ngayDat;
        this.giaVe = giaVe;
        this.trangThai = trangThai;
    }

    public VeTau(int id, String maVe, int idKhachHang, int idLichTrinh, int idGhe, int idNhanVien, LocalDateTime ngayDat, double giaVe, String trangThai) {
        this(maVe, idKhachHang, idLichTrinh, idGhe, idNhanVien, ngayDat, giaVe, trangThai);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaVe() {
        return maVe;
    }

    public void setMaVe(String maVe) {
        this.maVe = maVe;
    }

    public int getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(int idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public int getIdLichTrinh() {
        return idLichTrinh;
    }

    public void setIdLichTrinh(int idLichTrinh) {
        this.idLichTrinh = idLichTrinh;
    }

    public int getIdGhe() {
        return idGhe;
    }

    public void setIdGhe(int idGhe) {
        this.idGhe = idGhe;
    }

    public int getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public LocalDateTime getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(LocalDateTime ngayDat) {
        this.ngayDat = ngayDat;
    }

    public double getGiaVe() {
        return giaVe;
    }

    public void setGiaVe(double giaVe) {
        this.giaVe = giaVe;
    }

    public String getTrangThaiVe() {
        return trangThai;
    }

    public void setTrangThaiVe(String trangThai) {
        this.trangThai = trangThai;
    }
}
