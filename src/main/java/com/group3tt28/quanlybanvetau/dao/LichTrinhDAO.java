package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.LichTrinh;
import com.group3tt28.quanlybanvetau.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LichTrinhDAO {

    private static final String TEN_BANG = "lich_trinh";
    private static final String COT_ID = "id";
    private static final String COT_MA_LICH_TRINH = "ma_lich_trinh";
    private static final String COT_ID_TAU = "id_tau";
    private static final String COT_ID_TUYEN = "id_tuyen_duong";
    private static final String COT_NGAY_DI = "ngay_di";
    private static final String COT_NGAY_DEN = "ngay_den";
    private static final String COT_TRANG_THAI = "trang_thai";

    public boolean checktrung(String malichtrinh) {
        if (malichtrinh == null) {
            return false;
        }
        String sql = " SELECT " + COT_MA_LICH_TRINH + " FROM " + TEN_BANG + " WHERE " + COT_MA_LICH_TRINH + "= ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, malichtrinh);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi check lịch trình: " + e.getMessage(), e);
        }
        return false;
    }

    public boolean insert(LichTrinh lichTrinh) {
        if (lichTrinh == null) {
            return false;
        }
        String sql = "INSERT INTO " + TEN_BANG + "(" + COT_MA_LICH_TRINH + "," + COT_ID_TAU + "," + COT_ID_TUYEN +
                "," + COT_NGAY_DI + "," + COT_NGAY_DEN + "," + COT_TRANG_THAI + ")" + " VALUES (?,?,?,?,?,?)";

        Timestamp ngaydiSQL = null;
        if (lichTrinh.getNgayDi() != null) {
            ngaydiSQL = Timestamp.valueOf(lichTrinh.getNgayDi());
        }

        Timestamp ngaydenSQL = null;
        if (lichTrinh.getNgayDen() != null) {
            ngaydenSQL = Timestamp.valueOf(lichTrinh.getNgayDen());
        }

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, lichTrinh.getMaLichTrinh());
            ps.setInt(2, lichTrinh.getIdTau());
            ps.setInt(3, lichTrinh.getIdTuyenDuong());
            ps.setTimestamp(4, ngaydiSQL);
            ps.setTimestamp(5, ngaydenSQL);
            ps.setString(6, lichTrinh.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thêm lịch trình: " + e.getMessage(), e);
        }
    }

    public boolean update(LichTrinh lichTrinh) {
        if (lichTrinh == null) {
            return false;
        }
        String sql = "UPDATE " + TEN_BANG + " SET "
                + COT_ID_TAU + "=? ,"
                + COT_ID_TUYEN + "=? , "
                + COT_NGAY_DI + "=? ,"
                + COT_NGAY_DEN + "=? ,"
                + COT_TRANG_THAI + "=? "
                + "WHERE " + COT_MA_LICH_TRINH + "=?";

        Timestamp ngaydiSQL = null;
        if (lichTrinh.getNgayDi() != null) {
            ngaydiSQL = Timestamp.valueOf(lichTrinh.getNgayDi());
        }

        Timestamp ngaydenSQL = null;
        if (lichTrinh.getNgayDen() != null) {
            ngaydenSQL = Timestamp.valueOf(lichTrinh.getNgayDen());
        }

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, lichTrinh.getIdTau());
            ps.setInt(2, lichTrinh.getIdTuyenDuong());
            ps.setTimestamp(3, ngaydiSQL);
            ps.setTimestamp(4, ngaydenSQL);
            ps.setString(5, lichTrinh.getTrangThai());
            ps.setString(6, lichTrinh.getMaLichTrinh());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi sửa lịch trình: " + e.getMessage(), e);
        }
    }

    public boolean delete(int id) {
        if (id < 1) {
            return false;
        }
        String sql = "DELETE FROM " + TEN_BANG + " WHERE " + COT_ID + " = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi xóa lịch trình: " + e.getMessage(), e);
        }
    }

    public List<LichTrinh> getAll() {
        List<LichTrinh> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TEN_BANG;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Timestamp ngaydiSQL = rs.getTimestamp(COT_NGAY_DI);
                Timestamp ngaydenSQL = rs.getTimestamp(COT_NGAY_DEN);
                int id = rs.getInt(COT_ID);
                String malichtrinh = rs.getString(COT_MA_LICH_TRINH);
                int idTau = rs.getInt(COT_ID_TAU);
                int idTuyen = rs.getInt(COT_ID_TUYEN);
                LocalDateTime ngaydi = ngaydiSQL != null ? ngaydiSQL.toLocalDateTime() : null;
                LocalDateTime ngayden = ngaydenSQL != null ? ngaydenSQL.toLocalDateTime() : null;
                String trangthai = rs.getString(COT_TRANG_THAI);

                LichTrinh lichTrinh = new LichTrinh(id, malichtrinh, idTau, idTuyen, ngaydi, ngayden, trangthai);
                list.add(lichTrinh);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tải dữ liệu lịch trình: " + e.getMessage(), e);
        }
        return list;
    }

    // --- PHƯƠNG THỨC MỚI: TÌM KIẾM ---
    public List<LichTrinh> timKiemLichTrinh(String tuKhoa, int idTau, int idTuyen, String trangThai) {
        List<LichTrinh> list = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM " + TEN_BANG + " WHERE 1=1 ");

        // 1. Tìm theo từ khóa (Mã lịch trình)
        if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
            sql.append(" AND ").append(COT_MA_LICH_TRINH).append(" LIKE ? ");
            params.add("%" + tuKhoa.trim() + "%");
        }

        // 2. Tìm theo Tàu
        if (idTau > 0) {
            sql.append(" AND ").append(COT_ID_TAU).append(" = ? ");
            params.add(idTau);
        }

        // 3. Tìm theo Tuyến đường
        if (idTuyen > 0) {
            sql.append(" AND ").append(COT_ID_TUYEN).append(" = ? ");
            params.add(idTuyen);
        }

        // 4. Tìm theo Trạng thái
        if (trangThai != null && !trangThai.trim().isEmpty() && !trangThai.equalsIgnoreCase("Tất cả")) {
            sql.append(" AND ").append(COT_TRANG_THAI).append(" = ? ");
            params.add(trangThai);
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp ngaydiSQL = rs.getTimestamp(COT_NGAY_DI);
                    Timestamp ngaydenSQL = rs.getTimestamp(COT_NGAY_DEN);
                    int id = rs.getInt(COT_ID);
                    String maLichTrinh = rs.getString(COT_MA_LICH_TRINH);
                    int idTauDb = rs.getInt(COT_ID_TAU);
                    int idTuyenDb = rs.getInt(COT_ID_TUYEN);
                    LocalDateTime ngayDi = ngaydiSQL != null ? ngaydiSQL.toLocalDateTime() : null;
                    LocalDateTime ngayDen = ngaydenSQL != null ? ngaydenSQL.toLocalDateTime() : null;
                    String status = rs.getString(COT_TRANG_THAI);

                    LichTrinh lt = new LichTrinh(id, maLichTrinh, idTauDb, idTuyenDb, ngayDi, ngayDen, status);
                    list.add(lt);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm kiếm lịch trình: " + e.getMessage(), e);
        }
        return list;
    }
}