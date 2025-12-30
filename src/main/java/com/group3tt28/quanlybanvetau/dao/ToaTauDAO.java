package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.ToaTau;
import com.group3tt28.quanlybanvetau.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ToaTauDAO {

    private static final String TEN_BANG = "toa_tau";
    private static final String COT_ID = "id";
    private static final String COT_MA_TOA = "ma_toa";
    private static final String COT_ID_TAU = "id_tau";
    private static final String COT_ID_LOAI_TOA = "id_loai_toa";


    public boolean checkTrung(String maToa, int idTau, int idLoaiTru) {
        String sql = "SELECT id FROM " + TEN_BANG
                + " WHERE " + COT_MA_TOA + " = ? AND " + COT_ID_TAU + " = ? AND " + COT_ID + " != ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maToa);
            ps.setInt(2, idTau);
            ps.setInt(3, idLoaiTru);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insert(ToaTau toaTau) {
        if (toaTau == null) return false;
        String sql = "INSERT INTO " + TEN_BANG + " (" + COT_MA_TOA + "," + COT_ID_TAU + "," + COT_ID_LOAI_TOA + ") VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, toaTau.getMaToa());
            ps.setInt(2, toaTau.getIdTau());
            ps.setInt(3, toaTau.getIdLoaiToa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi thêm toa tàu: " + e.getMessage(), e);
        }
    }

    public boolean update(ToaTau toaTau) {
        if (toaTau == null) return false;
        String sql = "UPDATE " + TEN_BANG + " SET " + COT_MA_TOA + " = ?, " + COT_ID_TAU + " = ?, " + COT_ID_LOAI_TOA + " = ? " + " WHERE " + COT_ID + " = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, toaTau.getMaToa());
            ps.setInt(2, toaTau.getIdTau());
            ps.setInt(3, toaTau.getIdLoaiToa());
            ps.setInt(4, toaTau.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi cập nhật toa tàu: " + e.getMessage(), e);
        }
    }

    public boolean delete(int id) {
        if (id < 1) return false;
        String sql = "DELETE FROM " + TEN_BANG + " WHERE " + COT_ID + " = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi xóa toa tàu: " + e.getMessage(), e);
        }
    }

    public List<ToaTau> getAll() {
        List<ToaTau> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TEN_BANG;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ToaTau(
                        rs.getInt(COT_ID),
                        rs.getString(COT_MA_TOA),
                        rs.getInt(COT_ID_TAU),
                        rs.getInt(COT_ID_LOAI_TOA)
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi load toa tàu: " + e.getMessage(), e);
        }
        return list;
    }

    public List<ToaTau> timKiemToaTau(String tuKhoa, int idTau, int idLoaiToa) {
        List<ToaTau> list = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM " + TEN_BANG + " WHERE 1=1 ");

        if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
            sql.append(" AND ").append(COT_MA_TOA).append(" LIKE ? ");
            params.add("%" + tuKhoa.trim() + "%");
        }
        if (idTau > 0) {
            sql.append(" AND ").append(COT_ID_TAU).append(" = ? ");
            params.add(idTau);
        }
        if (idLoaiToa > 0) {
            sql.append(" AND ").append(COT_ID_LOAI_TOA).append(" = ? ");
            params.add(idLoaiToa);
        }

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ToaTau(rs.getInt(COT_ID), rs.getString(COT_MA_TOA), rs.getInt(COT_ID_TAU), rs.getInt(COT_ID_LOAI_TOA)));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi tìm kiếm: " + e.getMessage(), e);
        }
        return list;
    }
}