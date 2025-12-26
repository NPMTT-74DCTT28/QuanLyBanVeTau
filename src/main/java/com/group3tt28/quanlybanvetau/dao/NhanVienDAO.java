package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.NhanVien;
import com.group3tt28.quanlybanvetau.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public boolean checkTrung(String maNhanVien) {
        if (maNhanVien == null) {
            return false;
        }

        String sql = "SELECT " + COT_MA_NV + " FROM " + TEN_BANG
                + " WHERE " + COT_MA_NV + " = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNhanVien);
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

    public boolean insert(NhanVien nhanVien) {
        if (nhanVien == null) {
            return false;
        }

        String sql = "INSERT INTO " + TEN_BANG + " ("
                + COT_MA_NV + ", " + COT_MAT_KHAU + ", " + COT_HO_TEN + ", "
                + COT_NGAY_SINH + ", " + COT_GIOI_TINH + ", " + COT_SDT + ", "
                + COT_EMAIL + ", " + COT_DIA_CHI + ", " + COT_VAI_TRO
                + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Date sqlNgaySinh = null;
        if (nhanVien.getNgaySinh() != null) {
            sqlNgaySinh = Date.valueOf(nhanVien.getNgaySinh());
        }

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nhanVien.getMaNhanVien());
            ps.setString(2, nhanVien.getMatKhau());
            ps.setString(3, nhanVien.getHoTen());
            ps.setDate(4, sqlNgaySinh);
            ps.setString(5, nhanVien.getGioiTinh());
            ps.setString(6, nhanVien.getSdt());
            ps.setString(7, nhanVien.getEmail());
            ps.setString(8, nhanVien.getDiaChi());
            ps.setString(9, nhanVien.getVaiTro());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi he thong khi them nhan vien: " + e.getMessage(), e);
        }
    }

    public boolean update(NhanVien nhanVien) {
        if (nhanVien == null) {
            return false;
        }

        String sql = "UPDATE " + TEN_BANG + " SET "
                + COT_HO_TEN + " = ?, "
                + COT_NGAY_SINH + " =  ?, "
                + COT_GIOI_TINH + " =  ?, "
                + COT_SDT + " =  ?, "
                + COT_EMAIL + " =  ?, "
                + COT_DIA_CHI + " =  ?, "
                + COT_VAI_TRO + " =  ?"
                + " WHERE " + COT_MA_NV + " = ?";

        Date sqlNgaySinh = null;
        if (nhanVien.getNgaySinh() != null) {
            sqlNgaySinh = Date.valueOf(nhanVien.getNgaySinh());
        }

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nhanVien.getHoTen());
            ps.setDate(2, sqlNgaySinh);
            ps.setString(3, nhanVien.getGioiTinh());
            ps.setString(4, nhanVien.getSdt());
            ps.setString(5, nhanVien.getEmail());
            ps.setString(6, nhanVien.getDiaChi());
            ps.setString(7, nhanVien.getVaiTro());
            ps.setString(8, nhanVien.getMaNhanVien());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi he thong khi cap nhat nhan vien: " + e.getMessage(), e);
        }
    }

    public boolean delete(int id) {
        if (id < 1) {
            return false;
        }

        String sql = "DELETE FROM " + TEN_BANG + " WHERE " + COT_ID + " = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi he thong khi xoa nhan vien: " + e.getMessage(), e);
        }
    }

    public boolean doiMatKhau(int id, String matKhauDaBam) {
        String sql = "UPDATE " + TEN_BANG + " SET " + COT_MAT_KHAU + " = ? WHERE " + COT_ID + " = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, matKhauDaBam);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Co loi xay ra khi doi mat khau: " + e.getMessage(), e);
        }
    }

    public String getMatKhauById(int id) {
        String sql = "SELECT " + COT_MAT_KHAU + " FROM " + TEN_BANG + " WHERE " + COT_ID + " = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(COT_MAT_KHAU);
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi khi lay mat khau: " + e.getMessage(), e);
        }
        return null;
    }

    public List<NhanVien> getAll() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TEN_BANG;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Date sqlDate = rs.getDate(COT_NGAY_SINH);

                int id = rs.getInt(COT_ID);
                String maNhanVien = rs.getString(COT_MA_NV);
                String matKhau = rs.getString(COT_MAT_KHAU);
                String hoTen = rs.getString(COT_HO_TEN);
                LocalDate ngaySinh = (sqlDate != null) ? sqlDate.toLocalDate() : null;
                String gioiTinh = rs.getString(COT_GIOI_TINH);
                String sdt = rs.getString(COT_SDT);
                String email = rs.getString(COT_EMAIL);
                String diaChi = rs.getString(COT_DIA_CHI);
                String vaiTro = rs.getString(COT_VAI_TRO);

                NhanVien nv = new NhanVien(id, maNhanVien, matKhau, hoTen, ngaySinh, gioiTinh, sdt, email, diaChi, vaiTro);
                list.add(nv);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Co loi khi load du lieu nhan vien: " + e.getMessage(), e);
        }
        return list;
    }
}
