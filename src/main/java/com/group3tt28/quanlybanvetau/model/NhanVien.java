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
public class NhanVien {

    private int id;
    private String maNhanVien;
    private transient String matKhau;
    private String hoTen;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String sdt;
    private String email;
    private String diaChi;
    private String vaiTro;

    public NhanVien() {
    }

    public NhanVien(String maNhanVien, String hoTen, LocalDate ngaySinh, String gioiTinh, String sdt, String email, String diaChi, String vaiTro) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.email = email;
        this.diaChi = diaChi;
        this.vaiTro = vaiTro;
    }

    public NhanVien(String maNhanVien, String matKhau, String hoTen, LocalDate ngaySinh,
            String gioiTinh, String sdt, String email, String diaChi, String vaiTro) {
        this(maNhanVien, hoTen, ngaySinh, gioiTinh, sdt, email, diaChi, vaiTro);
        this.matKhau = matKhau;
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

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }
}
