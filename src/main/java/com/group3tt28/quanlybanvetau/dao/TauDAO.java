package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.Tau;
import com.group3tt28.quanlybanvetau.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TauDAO {

    private static final String TEN_BANG = "tau";
    private static final String COT_ID = "id";
    private static final String COT_MA_TAU = "ma_tau";
    private static final String COT_TEN_TAU = "ten_tau";

    public boolean checkTrung(String maTau, int ID){
        if(maTau == null){
            return false;
        }

        String sql = " SELECT " + COT_MA_TAU +" FROM " + TEN_BANG
                + " WHERE " + COT_MA_TAU + " = ? AND " + COT_ID + " != ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maTau);
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

    public boolean insert(Tau tau) {
        if (tau == null) {
            return false;
        }

        String sql = "INSERT INTO " + TEN_BANG + " ("
                + COT_MA_TAU + ","
                + COT_TEN_TAU
                + ") VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tau.getMaTau());
            ps.setString(2, tau.getTenTau());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi he thong khi them tau: " + e.getMessage(), e);
        }
    }

    public boolean update(Tau tau) {
        if (tau == null) {
            return false;
        }

        String sql = "UPDATE " + TEN_BANG + " SET "
                + COT_MA_TAU + " = ?, "
                + COT_TEN_TAU + " = ?"
                + " WHERE " + COT_ID + " = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tau.getMaTau());
            ps.setString(2, tau.getTenTau());
            ps.setInt(3, tau.getId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Xay ra loi he thong khi cap nhat tau: " + e.getMessage(), e);
        }
    }

    public boolean delete(String MaTau) {
        if (MaTau == null) {
            return false;
        }

        String sql = "DELETE FROM " + TEN_BANG
                + " WHERE " + COT_MA_TAU + " = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, MaTau);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi he thong khi xoa tau: " + e.getMessage(), e);
        }
    }

    public List<Tau> getAll() {
        List<Tau> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TEN_BANG;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {

                int id = rs.getInt(COT_ID);
                String maTau = rs.getString(COT_MA_TAU);
                String tenTau = rs.getString(COT_TEN_TAU);

                Tau tau = new Tau(id, maTau, tenTau);
                list.add(tau);
            }
        } catch (Exception e) {
            throw new RuntimeException("Xay ra loi khi load tau: " + e.getMessage(), e);
        }
        return list;
    }
}
