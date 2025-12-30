package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.KhachHang;
import com.group3tt28.quanlybanvetau.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {

    private static final String TEN_BANG = "khach_hang";
    private static final String COT_ID = "id";
    private static final String COT_CCCD = "cccd";
    private static final String COT_HO_TEN = "ho_ten";
    private static final String COT_NGAY_SINH = "ngay_sinh";
    private static final String COT_GIOI_TINH = "gioi_tinh";
    private static final String COT_SDT = "sdt";
    private static final String COT_DIA_CHI = "dia_chi";

    public boolean checkTrung(String cccd) {
        if (cccd == null || cccd.trim().isEmpty()) {
            return false;
        }
        String sql = "SELECT " + COT_CCCD + " FROM " + TEN_BANG
                + " WHERE " + COT_CCCD + " = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cccd);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean insert(KhachHang khachHang) {
        if (khachHang == null) {
            return false;
        }

        String sql = "INSERT INTO " + TEN_BANG + " ("
                + COT_CCCD + ", " + COT_HO_TEN + ", " + COT_NGAY_SINH + ", "
                + COT_GIOI_TINH + ", " + COT_SDT + ", " + COT_DIA_CHI
                + ") VALUES(?, ?, ?, ?, ?, ?)";

        Date sqlNgaySinh = null;
        if (khachHang.getNgaySinh() != null) {
            sqlNgaySinh = Date.valueOf(khachHang.getNgaySinh());
        }

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, khachHang.getCccd());
            ps.setString(2, khachHang.getHoTen());
            ps.setDate(3, sqlNgaySinh);
            ps.setString(4, khachHang.getGioiTinh());
            ps.setString(5, khachHang.getSdt());
            ps.setString(6, khachHang.getDiaChi());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi he thong khi them khach hang: " + e.getMessage(), e);
        }
    }

    public boolean update(KhachHang khachHang) {
        if (khachHang == null) {
            return false;
        }

        String sql = "UPDATE " + TEN_BANG + " SET "
                + COT_HO_TEN + " = ?, "
                + COT_NGAY_SINH + " =  ?, "
                + COT_GIOI_TINH + " =  ?, "
                + COT_SDT + " =  ?, "
                + COT_DIA_CHI + " =  ? "
                + " WHERE " + COT_CCCD + " = ?";

        Date sqlNgaySinh = null;
        if (khachHang.getNgaySinh() != null) {
            sqlNgaySinh = Date.valueOf(khachHang.getNgaySinh());
        }

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, khachHang.getHoTen());
            ps.setDate(2, sqlNgaySinh);
            ps.setString(3, khachHang.getGioiTinh());
            ps.setString(4, khachHang.getSdt());
            ps.setString(5, khachHang.getDiaChi());
            ps.setString(6, khachHang.getCccd());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi he thong khi cap nhat khach hang: " + e.getMessage(), e);
        }
    }

    public boolean delete(String CCCD) {
        if (CCCD == null) {
            return false;
        }

        String sql = "DELETE FROM " + TEN_BANG + " WHERE " + COT_CCCD + " = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, CCCD);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi he thong khi xoa khach hang: " + e.getMessage(), e);
        }
    }

    public List<KhachHang> getAll() {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TEN_BANG;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Date sqlDate = rs.getDate(COT_NGAY_SINH);

                int id = rs.getInt(COT_ID);
                String cccd = rs.getString(COT_CCCD);
                String hoTen = rs.getString(COT_HO_TEN);
                LocalDate ngaySinh = (sqlDate != null) ? sqlDate.toLocalDate() : null;
                String gioiTinh = rs.getString(COT_GIOI_TINH);
                String sdt = rs.getString(COT_SDT);
                String diaChi = rs.getString(COT_DIA_CHI);

                KhachHang kh = new KhachHang(id, cccd, hoTen, ngaySinh, gioiTinh, sdt, diaChi);
                list.add(kh);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Co loi khi load du lieu nhan vien: " + e.getMessage(), e);
        }
        return list;
    }

    public List<KhachHang> timKiemKH(String tuKhoa) {
        List<KhachHang> list = new ArrayList<>();
        String sql = ("SELECT * FROM " + TEN_BANG + " WHERE " + COT_CCCD + " LIKE ? OR " + COT_HO_TEN + " LIKE ? ");

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + tuKhoa + "%");
            ps.setString(2, "%" + tuKhoa + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt(COT_ID);
                    String cccd = rs.getString(COT_CCCD);
                    String hoTen = rs.getString(COT_HO_TEN);
                    LocalDate ngaySinh = rs.getDate(COT_NGAY_SINH).toLocalDate();
                    String gioiTinh = rs.getString(COT_GIOI_TINH);
                    String sdt = rs.getString(COT_SDT);
                    String diaChi = rs.getString(COT_DIA_CHI);
                    KhachHang khachHang = new KhachHang(id, cccd, hoTen, ngaySinh, gioiTinh, sdt, diaChi);
                    list.add(khachHang);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm kiếm khách hàng: " + e.getMessage());
        }
        return list;
    }


}
