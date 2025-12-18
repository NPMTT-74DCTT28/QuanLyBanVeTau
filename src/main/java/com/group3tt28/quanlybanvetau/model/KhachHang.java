/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.model;

import java.time.LocalDate;

/**
 *
 * @author qphwn
 */
public class KhachHang extends Person {

    private int id;
    private String cccd;

    public KhachHang() {
    }

    public KhachHang(String cccd, String hoTen, LocalDate ngaySinh, String gioiTinh, String sdt, String diaChi) {
        super(hoTen, ngaySinh, gioiTinh, sdt, diaChi);
        this.cccd = cccd;
    }

    public KhachHang(int id, String cccd, String hoTen, LocalDate ngaySinh, String gioiTinh, String sdt, String diaChi) {
        super(hoTen, ngaySinh, gioiTinh, sdt, diaChi);
        this.id = id;
        this.cccd = cccd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }
}
