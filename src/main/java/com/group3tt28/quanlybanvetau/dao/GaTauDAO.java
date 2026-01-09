package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.GaTau;
import com.group3tt28.quanlybanvetau.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GaTauDAO {

    private static final String TEN_BANG = "ga_tau";
    private static final String COT_ID = "id";
    private static final String COT_MA_GA = "ma_ga";
    private static final String COT_TEN_GA = "ten_ga";
    private static final String COT_DIA_CHI = "dia_chi";
    private static final String COT_THANH_PHO = "thanh_pho";

    public boolean checkTrung(String maGa) {
        if (maGa == null || maGa.trim().isEmpty()) {
            return false;
        }
        String sql = "SELECT " + COT_MA_GA + " FROM " + TEN_BANG + " WHERE " + COT_MA_GA + " = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maGa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean insert(GaTau gaTau) {
        if (gaTau == null) {
            return false;
        }

        String sql = "INSERT INTO " + TEN_BANG + "(" + COT_MA_GA + ", " + COT_TEN_GA + "," + COT_DIA_CHI + "," + COT_THANH_PHO + ")"
                + " VALUES(?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, gaTau.getMaGa());
            ps.setString(2, gaTau.getTenGa());
            ps.setString(3, gaTau.getDiaChi());
            ps.setString(4, gaTau.getThanhPho());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("xay ra loi khi them ga tau:" + e.getMessage(), e);
        }
    }

    public boolean update(GaTau gaTau) {
        if (gaTau == null) {
            return false;
        }
        String sql = "UPDATE " + TEN_BANG + " SET "
                + COT_TEN_GA + " = ?, "
                + COT_DIA_CHI + " =?, "
                + COT_THANH_PHO + " =? "
                + " WHERE " + COT_MA_GA + " =?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, gaTau.getTenGa());
            ps.setString(2, gaTau.getDiaChi());
            ps.setString(3, gaTau.getThanhPho());
            ps.setString(4, gaTau.getMaGa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi khi sua ga tau:" + e.getMessage(), e);
        }
    }

    public boolean delete(String maGa) {
        if (maGa == null) {
            return false;
        }
        String sql = "DELETE FROM " + TEN_BANG + " WHERE " + COT_MA_GA + " = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maGa);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi khi xoa:" + e.getMessage(), e);
        }
    }

    public List<GaTau> getAll() {
        List<GaTau> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TEN_BANG;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt(COT_ID);
                String maGa = rs.getString(COT_MA_GA);
                String tenGa = rs.getString(COT_TEN_GA);
                String diaChi = rs.getString(COT_DIA_CHI);
                String thanhPho = rs.getString(COT_THANH_PHO);

                GaTau gt = new GaTau(id, maGa, tenGa, diaChi, thanhPho);
                list.add(gt);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi khi hien thi ga tau:" + e.getMessage(), e);
        }
        return list;
    }

    public List<GaTau> timkiem(String keyword) {
        List<GaTau> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TEN_BANG + " WHERE "
                + COT_MA_GA + " LIKE ? OR "
                + COT_TEN_GA + " LIKE ? ";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt(COT_ID);
                    String maGa = rs.getString(COT_MA_GA);
                    String tenGa = rs.getString(COT_TEN_GA);
                    String diaChi = rs.getString(COT_DIA_CHI);
                    String thanhPho = rs.getString(COT_THANH_PHO);
                    GaTau gaTau = new GaTau(id, maGa, tenGa, diaChi, thanhPho);
                    list.add(gaTau);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm kiếm ga tàu: " + e.getMessage(), e);
        }
        return list;
    }
}
