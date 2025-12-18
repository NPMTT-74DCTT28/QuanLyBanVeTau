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
public class NhanVien extends Person {

    private int id;
    private String maNhanVien;
    private transient String matKhau;
    private String email;
    private String vaiTro;

    public NhanVien() {
    }

    public NhanVien(String maNhanVien, String matKhau, String hoTen, LocalDate ngaySinh,
            String gioiTinh, String sdt, String email, String diaChi, String vaiTro) {
        super(hoTen, ngaySinh, gioiTinh, sdt, diaChi);
        this.maNhanVien = maNhanVien;
        this.matKhau = matKhau;
        this.email = email;
        this.vaiTro = vaiTro;
    }

    public NhanVien(int id, String maNhanVien, String matKhau, String hoTen, LocalDate ngaySinh,
            String gioiTinh, String sdt, String email, String diaChi, String vaiTro) {
        this(maNhanVien, matKhau, hoTen, ngaySinh, gioiTinh, sdt, email, diaChi, vaiTro);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }
}
