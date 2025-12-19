/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.NhanVien;
import com.group3tt28.quanlybanvetau.util.DBConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author qphwn
 */
public class NhanVienDAO {

    private static final String TEN_BANG = "nhan_vien";
    private static final String COT_ID = "id";
    private static final String COT_MA_NV = "ma_nhan_vien";
    private static final String COT_MAT_KHAU = "mat_khau";
    private static final String COT_HO_TEN = "ho_ten";
    private static final String COT_NGAY_SINH = "ngay_sinh";
    private static final String COT_GIOI_TINH = "gioi_tinh";
    private static final String COT_SDT = "sdt";
    private static final String COT_EMAIL = "email";
    private static final String COT_DIA_CHI = "dia_chi";
    private static final String COT_VAI_TRO = "vai_tro";

    public int insert(NhanVien nhanVien) {
        String maNhanVien = nhanVien.getMaNhanVien();
        String matKhau = nhanVien.getMatKhau();
        String hoTen = nhanVien.getHoTen();
        Date ngaySinh = Date.valueOf(nhanVien.getNgaySinh());
        String gioiTinh = nhanVien.getGioiTinh();
        String sdt = nhanVien.getSdt();
        String email = nhanVien.getEmail();
        String diaChi = nhanVien.getDiaChi();
        String vaiTro = nhanVien.getVaiTro();

        String sql = "INSERT INTO " + TEN_BANG + " ("
                + COT_MA_NV + ", " + COT_MAT_KHAU + ", " + COT_HO_TEN + ", "
                + COT_NGAY_SINH + ", " + COT_GIOI_TINH + ", " + COT_SDT + ", "
                + COT_EMAIL + ", " + COT_DIA_CHI + ", " + COT_VAI_TRO
                + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNhanVien);
            ps.setString(2, matKhau);
            ps.setString(3, hoTen);
            ps.setDate(4, ngaySinh);
            ps.setString(5, gioiTinh);
            ps.setString(6, sdt);
            ps.setString(7, email);
            ps.setString(8, diaChi);
            ps.setString(9, vaiTro);

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Co loi he thong khi them nhan vien: ", e);
        }
    }

    public int update(NhanVien nhanVien) {
        //Viết code UPDATE (đặt trong try-cacth)
        //return ps.executeUpdate() (PreparedStatement)
        return 0;
    }

    public int delete(int id) {
        //Viết code DELETE (đặt trong try-cacth)
        //return ps.executeUpdate() (PreparedStatement)
        return 0;
    }

    public List<NhanVien> getAll() {
        List<NhanVien> list = new ArrayList<>();
        //Viết code SELECT *
        //dùng PreparedStatement + ResultSet
        return list;
    }
}
