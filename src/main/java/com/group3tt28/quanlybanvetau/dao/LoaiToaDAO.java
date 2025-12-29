package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.LoaiToa;
import com.group3tt28.quanlybanvetau.model.Tau;
import com.group3tt28.quanlybanvetau.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoaiToaDAO {

    private static final String TEN_BANG = "loai_toa";
    private static final String COT_ID = "id";
    private static final String COT_TEN_LOAI = "ten_loai";
    private static final String COT_HE_SO_GIA = "he_so_gia";

    public boolean checkTrung(String tenLoai, int ID) {
        if (tenLoai == null) {
            return false;
        }

        String sql = " SELECT " + COT_TEN_LOAI + " FROM " + TEN_BANG
                + " WHERE " + COT_TEN_LOAI + " = ? AND " + COT_ID + " != ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenLoai);
            ps.setInt(2, ID);
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

    public boolean insert(LoaiToa loaiToa) {
        if (loaiToa == null) {
            return false;
        }

        String sql = "INSERT INTO " + TEN_BANG + " ("
                + COT_TEN_LOAI + ", "
                + COT_HE_SO_GIA
                + ") VALUES(?, ?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, loaiToa.getTenLoai());
            ps.setDouble(2, loaiToa.getHeSoGia());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi he thuong khi them loai toa: " + e.getMessage(), e);
        }
    }

    public boolean update(LoaiToa loaiToa) {
        if (loaiToa == null) {
            return false;
        }

        String sql = "UPDATE " + TEN_BANG + " SET "
                + COT_TEN_LOAI + " = ?, "
                + COT_HE_SO_GIA + " = ? "
                + " WHERE " + COT_ID + " = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, loaiToa.getTenLoai());
            ps.setDouble(2, loaiToa.getHeSoGia());
            ps.setInt(3, loaiToa.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi he thong khi cap nhat loai toa: " + e.getMessage(), e);
        }
    }

    public boolean delete(String Tenloai) {
        if (Tenloai == null) {
            return false;
        }

        String sql = "DELETE FROM " + TEN_BANG
                + " WHERE " + COT_TEN_LOAI + " = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, Tenloai);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi he thong khi xoa loai toa" + e.getMessage(), e);
        }
    }

    public List<LoaiToa> getAll() {
        List<LoaiToa> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TEN_BANG;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {

                int id = rs.getInt(COT_ID);
                String tenLoai = rs.getString(COT_TEN_LOAI);
                Double heSoGia = rs.getDouble(COT_HE_SO_GIA);

                LoaiToa lt = new LoaiToa(id, tenLoai, heSoGia);
                list.add(lt);
            }
        } catch (Exception e) {
            throw new RuntimeException("Co loi khi load du lieu loai toa " + e.getMessage(), e);
        }
        return list;
    }
    public List<LoaiToa> timKiemLoaiToa(String tuKhoa){
        List<LoaiToa> list = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM " + TEN_BANG + " WHERE 1 = 1 ");
        if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
            sql.append(" AND (")
                    .append(COT_TEN_LOAI).append(" LIKE ? OR ")
                    .append(COT_HE_SO_GIA).append(" LIKE ? ")
                    .append(") ");

            String searchKeyword = "%" + tuKhoa + "%";
            for (int i = 1; i <= 2; i++) {
                params.add(searchKeyword);
            }
        }
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql.toString());) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt(COT_ID);
                    String tenLoai = rs.getString(COT_TEN_LOAI);
                    double heSoGia = rs.getDouble(COT_HE_SO_GIA);

                    LoaiToa loaiToa = new LoaiToa(id, tenLoai, heSoGia);
                    list.add(loaiToa);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm kiếm nhân viên: " + e.getMessage(), e);
        }
        return list;
    }
}
