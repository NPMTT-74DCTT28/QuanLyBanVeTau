package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.Ghe;
import com.group3tt28.quanlybanvetau.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GheDAO {

    private static final String TEN_BANG = "ghe";
    private static final String COT_ID = "id";
    private static final String COT_SO_GHE = "so_ghe";
    private static final String COT_ID_TOA_TAU = "id_toa_tau";

    public boolean checkTrung(String soGhe, int idToaTau, int idGhe) {
        if (soGhe == null) {
            return false;
        }

        String sql = " SELECT " + COT_SO_GHE + " FROM " + TEN_BANG
                + " WHERE ( " + COT_SO_GHE + " = ? AND " + COT_ID_TOA_TAU + " = ?) AND id != ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, soGhe);
            ps.setInt(2, idToaTau);
            ps.setInt(3, idGhe);
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

    public boolean insert(Ghe ghe) {
        if (ghe == null) {
            return false;
        }

        String sql = "INSERT INTO " + TEN_BANG + " ("
                + COT_SO_GHE + ","
                + COT_ID_TOA_TAU
                + ") VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ghe.getSoGhe());
            ps.setInt(2, ghe.getIdToaTau());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi he thong khi them ghe: " + e.getMessage(), e);
        }

    }

    public boolean update(Ghe ghe) {
        if (ghe == null) {
            return false;
        }

        String sql = "UPDATE " + TEN_BANG + " SET "
                + COT_SO_GHE + " = ?, "
                + COT_ID_TOA_TAU + " = ? "
                + " WHERE " + COT_ID + " = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ghe.getSoGhe());
            ps.setInt(2, ghe.getIdToaTau());
            ps.setInt(3, ghe.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi he thong khi cap nhat ghe:" + e.getMessage(), e);
        }
    }

    public boolean delete(String SoGhe) {
        if (SoGhe == null) {
            return false;
        }

        String sql = "DELETE FROM " + TEN_BANG
                + " WHERE " + COT_SO_GHE + " = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, SoGhe);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi he thong khi xoa ghe: " + e.getMessage(), e);
        }
    }

    public List<Ghe> getAll() {
        List<Ghe> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TEN_BANG;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {

                int id = rs.getInt(COT_ID);
                String soGhe = rs.getString(COT_SO_GHE);
                int idToaTau = rs.getInt(COT_ID_TOA_TAU);

                Ghe ghe = new Ghe(id, soGhe, idToaTau);
                list.add(ghe);
            }
        } catch (Exception e) {
            throw new RuntimeException("Xay ra loi khi load ghe: " + e.getMessage(), e);
        }
        return list;
    }
    public List<Ghe> timKiemGhe(String tuKhoa){
        List<Ghe> list = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM " + TEN_BANG + " WHERE 1 = 1 ");
        if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
            sql.append(" AND (")
                    .append(COT_SO_GHE).append(" LIKE ? OR ")
                    .append(COT_ID_TOA_TAU).append(" LIKE ? ")
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
                    String soGhe = rs.getString(COT_SO_GHE);
                    int idToaTau = rs.getInt(COT_ID_TOA_TAU);

                    Ghe ghe = new Ghe(id, soGhe, idToaTau);
                    list.add(ghe);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm kiếm nhân viên: " + e.getMessage(), e);
        }
        return list;
    }
}
