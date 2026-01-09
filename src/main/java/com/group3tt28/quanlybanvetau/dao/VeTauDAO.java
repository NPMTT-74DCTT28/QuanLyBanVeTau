package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.VeTau;
import com.group3tt28.quanlybanvetau.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VeTauDAO {

    private static final String TEN_BANG = "ve_tau";
    private static final String COT_ID = "id";
    private static final String COT_MA_VE = "ma_ve";
    private static final String COT_ID_KH = "id_khach_hang";
    private static final String COT_ID_LICH_TRINH = "id_lich_trinh";
    private static final String COT_ID_GHE = "id_ghe";
    private static final String COT_ID_NHAN_VIEN = "id_nhan_vien";
    private static final String COT_NGAY_DAT = "ngay_dat";
    private static final String COT_GIA_VE = "gia_ve";
    private static final String COT_TRANG_THAI = "trang_thai";

    public boolean checkTrung(String maVe, int idLichtrinh, int idGhe, int id) {

        if (maVe == null || maVe.trim().isEmpty()) {
            return false;
        }
        String sql = "Select " + COT_MA_VE + " from " + TEN_BANG
                + " where " + COT_MA_VE + " = ? or " + " ( " + COT_ID_LICH_TRINH + " =? and " + COT_ID_GHE + " =?) and " + COT_ID + " !=?  ";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maVe);
            ps.setInt(2, idLichtrinh);
            ps.setInt(3, idGhe);
            ps.setInt(4, id);


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

    public boolean isGheDaDat(int idLichTrinh, int idGhe, String maVeHienTai) {
        String sql = "SELECT COUNT(*) FROM " + TEN_BANG +
                " WHERE " + COT_ID_LICH_TRINH + " = ? AND " + COT_ID_GHE + " = ? " +
                " AND " + COT_TRANG_THAI + " != N'Đã hủy' " +
                " AND " + COT_MA_VE + " != ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLichTrinh);
            ps.setInt(2, idGhe);
            ps.setString(3, maVeHienTai == null ? "" : maVeHienTai);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insert(VeTau veTau) {
        if (veTau == null) {
            return false;
        }
        String sql = "Insert into " + TEN_BANG + " ("
                + COT_MA_VE + ", " + COT_ID_KH + ", " + COT_ID_LICH_TRINH + ", "
                + COT_ID_GHE + ", " + COT_ID_NHAN_VIEN + ", "
                + COT_GIA_VE + ", " + COT_TRANG_THAI
                + ") Values (?,?,?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, veTau.getMaVe());
            ps.setInt(2, veTau.getIdKhachHang());
            ps.setInt(3, veTau.getIdLichTrinh());
            ps.setInt(4, veTau.getIdGhe());
            ps.setInt(5, veTau.getIdNhanVien());
            ps.setDouble(6, veTau.getGiaVe());
            ps.setString(7, veTau.getTrangThaiVe());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi he thong khi them ve tau: " + e.getMessage(), e);
        }

    }

    public boolean update(VeTau veTau) {
        if (veTau == null) {
            return false;
        }

        String sql = "UPDATE " + TEN_BANG + " SET "

                + COT_ID_KH + " =  ?, "
                + COT_ID_LICH_TRINH + " =  ?, "
                + COT_ID_GHE + " =  ?, "
                + COT_GIA_VE + " =  ?, "
                + COT_TRANG_THAI + " =  ?"
                + " WHERE " + COT_MA_VE + " = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, veTau.getIdKhachHang());
            ps.setInt(2, veTau.getIdLichTrinh());
            ps.setInt(3, veTau.getIdGhe());
            ps.setDouble(4, veTau.getGiaVe());
            ps.setString(5, veTau.getTrangThaiVe());
            ps.setString(6, veTau.getMaVe());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi hệ thống: " + e.getMessage(), e);
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
            throw new RuntimeException("Xay ra loi he thong khi xoa ve tau: " + e.getMessage(), e);
        }
    }

    public List<VeTau> getAll() {
        List<VeTau> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TEN_BANG;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Timestamp sqlNgayDat = rs.getTimestamp(COT_NGAY_DAT);

                int id = rs.getInt(COT_ID);
                String maVe = rs.getString(COT_MA_VE);
                int idKhachHang = rs.getInt(COT_ID_KH);
                int idLichTrinh = rs.getInt(COT_ID_LICH_TRINH);
                int idGhe = rs.getInt(COT_ID_GHE);
                int idNhanVien = rs.getInt(COT_ID_NHAN_VIEN);
                LocalDateTime ngayDat = (sqlNgayDat != null) ? sqlNgayDat.toLocalDateTime() : null;
                double giaVe = rs.getDouble(COT_GIA_VE);
                String trangThai = rs.getString(COT_TRANG_THAI);

                VeTau vt = new VeTau(id, maVe, idKhachHang, idLichTrinh, idGhe, idNhanVien, ngayDat, giaVe, trangThai);
                list.add(vt);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Co loi khi load du lieu ve tau: " + e.getMessage(), e);
        }
        return list;
    }

    public List<VeTau> timkiem(String tuKhoa) {
        List<VeTau> list = new ArrayList<>();
        String sql = ("SELECT * FROM " + TEN_BANG
                + " WHERE " + COT_MA_VE + " LIKE ? OR "
                + COT_ID_LICH_TRINH + " LIKE ? ");
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + tuKhoa + "%");
            ps.setString(2, "%" + tuKhoa + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp sqlNgayDat = rs.getTimestamp(COT_NGAY_DAT);

                    int id = rs.getInt(COT_ID);
                    String maVe = rs.getString(COT_MA_VE);
                    int idKhachHang = rs.getInt(COT_ID_KH);
                    int idLichTrinh = rs.getInt(COT_ID_LICH_TRINH);
                    int idGhe = rs.getInt(COT_ID_GHE);
                    int idNhanVien = rs.getInt(COT_ID_NHAN_VIEN);
                    LocalDateTime ngayDat = (sqlNgayDat != null) ? sqlNgayDat.toLocalDateTime() : null;
                    double giaVe = rs.getDouble(COT_GIA_VE);
                    String trangThai = rs.getString(COT_TRANG_THAI);

                    VeTau vt = new VeTau(id, maVe, idKhachHang, idLichTrinh, idGhe, idNhanVien, ngayDat, giaVe, trangThai);
                    list.add(vt);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Co loi khi load du lieu ve tau: " + e.getMessage(), e);
        }
        return list;
    }
}
