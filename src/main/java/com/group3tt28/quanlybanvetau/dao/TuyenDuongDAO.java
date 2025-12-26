package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.TuyenDuong;
import com.group3tt28.quanlybanvetau.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TuyenDuongDAO {

    private static final String TEN_BANG = "tuyen_duong";
    private static final String COT_ID = "id";
    private static final String COT_MA_TUYEN = "ma_tuyen";
    private static final String COT_TEN_TUYEN = "ten_tuyen";
    private static final String COT_ID_GA_DI = "id_ga_di";
    private static final String COT_ID_GA_DEN = "id_ga_den";
    private static final String COT_KHOANG_CACH = "khoang_cach_km";
    private static final String COT_GIA_CB = "gia_co_ban";

    public boolean checkTrung(String maTuyen) {
        if (maTuyen == null || maTuyen.trim().isEmpty()) {
            return false;
        }

        String sql = "SELECT " + COT_MA_TUYEN + " FROM " + TEN_BANG + " WHERE " + COT_MA_TUYEN + " = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maTuyen);
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

    public boolean insert(TuyenDuong tuyenDuong) {
        if (tuyenDuong == null) {
            return false;
        }
        String sql = "INSERT INTO " + TEN_BANG + "(" + COT_MA_TUYEN + ","
                + COT_TEN_TUYEN + ","
                + COT_ID_GA_DI + ","
                + COT_ID_GA_DEN + ","
                + COT_KHOANG_CACH + ","
                + COT_GIA_CB + ")"
                + " VALUES(?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tuyenDuong.getMaTuyen());
            ps.setString(2, tuyenDuong.getTenTuyen());
            ps.setInt(3, tuyenDuong.getIdGaDi());
            ps.setInt(4, tuyenDuong.getIdGaDen());
            ps.setDouble(5, tuyenDuong.getKhoangCachKm());
            ps.setDouble(6, tuyenDuong.getGiaCoBan());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Xay ra loi khi them tuyen duong:" + e.getMessage(), e);
        }
    }

    public boolean update(TuyenDuong tuyenDuong) {
        if (tuyenDuong == null) {
            return false;
        }
        String sql = "UPDATE " + TEN_BANG + " SET "
                + COT_TEN_TUYEN + " = ?,"
                + COT_ID_GA_DI + " = ?,"
                + COT_ID_GA_DEN + " = ?,"
                + COT_KHOANG_CACH + " = ?,"
                + COT_GIA_CB + " = ? "
                + " WHERE " + COT_MA_TUYEN + "=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tuyenDuong.getTenTuyen());
            ps.setInt(2, tuyenDuong.getIdGaDi());
            ps.setInt(3, tuyenDuong.getIdGaDen());
            ps.setDouble(4, tuyenDuong.getKhoangCachKm());
            ps.setDouble(5, tuyenDuong.getGiaCoBan());
            ps.setString(6, tuyenDuong.getMaTuyen());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Xay ra loi khi sua tuyen duong:" + e.getMessage(), e);
        }
    }

    public boolean delete(int id) {
        if (id < 1) {
            return false;
        }
        String sql = "DELETE FROM " + TEN_BANG + " WHERE " + COT_ID + " = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Xay ra loi khi xoa:" + e.getMessage(), e);
        }
    }

    public List<TuyenDuong> getAll() {
        List<TuyenDuong> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TEN_BANG;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt(COT_ID);
                String maTuyen = rs.getString(COT_MA_TUYEN);
                String tenTuyen = rs.getString(COT_TEN_TUYEN);
                int idGaDI = rs.getInt(COT_ID_GA_DI);
                int idGaDen = rs.getInt(COT_ID_GA_DEN);
                double khoangCachKm = rs.getDouble(COT_KHOANG_CACH);
                double giaCoBan = rs.getDouble(COT_GIA_CB);

                TuyenDuong td = new TuyenDuong(id, maTuyen, tenTuyen, idGaDI, idGaDen, khoangCachKm, giaCoBan);
                list.add(td);
            }
        } catch (Exception e) {
            throw new RuntimeException("Xay ra loi khi hien thi tuyen duong:" + e.getMessage(), e);
        }
        return list;
    }
}
