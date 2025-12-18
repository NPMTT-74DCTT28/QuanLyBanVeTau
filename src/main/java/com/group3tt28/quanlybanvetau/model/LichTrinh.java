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
public class LichTrinh {

    private int id;
    private String maLichTrinh;
    private int idTau;
    private int idTuyenDuong;
    private LocalDateTime ngayDi;
    private LocalDateTime ngayDen;
    private String trangThai;

    public LichTrinh() {
    }

    public LichTrinh(String maLichTrinh, int idTau, int idTuyenDuong,
            LocalDateTime ngayDi, LocalDateTime ngayDen, String trangThai) {
        this.maLichTrinh = maLichTrinh;
        this.idTau = idTau;
        this.idTuyenDuong = idTuyenDuong;
        this.ngayDi = ngayDi;
        this.ngayDen = ngayDen;
        this.trangThai = trangThai;
    }

    public LichTrinh(int id, String maLichTrinh, int idTau, int idTuyenDuong,
            LocalDateTime ngayDi, LocalDateTime ngayDen, String trangThai) {
        this(maLichTrinh, idTau, idTuyenDuong, ngayDi, ngayDen, trangThai);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaLichTrinh() {
        return maLichTrinh;
    }

    public void setMaLichTrinh(String maLichTrinh) {
        this.maLichTrinh = maLichTrinh;
    }

    public int getIdTau() {
        return idTau;
    }

    public void setIdTau(int idTau) {
        this.idTau = idTau;
    }

    public int getIdTuyenDuong() {
        return idTuyenDuong;
    }

    public void setIdTuyenDuong(int idTuyenDuong) {
        this.idTuyenDuong = idTuyenDuong;
    }

    public LocalDateTime getNgayDi() {
        return ngayDi;
    }

    public void setNgayDi(LocalDateTime ngayDi) {
        this.ngayDi = ngayDi;
    }

    public LocalDateTime getNgayDen() {
        return ngayDen;
    }

    public void setNgayDen(LocalDateTime ngayDen) {
        this.ngayDen = ngayDen;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
