package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.dto.*;
import com.group3tt28.quanlybanvetau.util.DBConnection;
import org.jfree.data.category.DefaultCategoryDataset;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThongKeDAO {

    private static ThongKeDAO instance;

    public static ThongKeDAO getInstance() {
        if (instance == null) {
            instance = new ThongKeDAO();
        }
        return instance;
    }

    public List<DoanhThuNgay> getDoanhThuTheoNgay(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        List<DoanhThuNgay> list = new ArrayList<>();
        String sql = "{CALL sp_ThongKeDoanhThuTheoNgay(?, ?)}";
        try (Connection conn = DBConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {
            cs.setDate(1, Date.valueOf(tuNgay));
            cs.setDate(2, Date.valueOf(denNgay));

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    String ngay = rs.getString("ngay");
                    int soVeBan = rs.getInt("so_ve_ban");
                    double doanhThu = rs.getDouble("doanh_thu");

                    list.add(new DoanhThuNgay(ngay, soVeBan, doanhThu));
                }
            }
        }
        return list;
    }

    public List<DoanhThuTuyen> getDoanhThuTheoTuyen(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        List<DoanhThuTuyen> list = new ArrayList<>();
        String sql = "{CALL sp_ThongKeDoanhThuTheoTuyen(?, ?)}";
        try (Connection conn = DBConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {
            cs.setDate(1, Date.valueOf(tuNgay));
            cs.setDate(2, Date.valueOf(denNgay));

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    String tenTuyen = rs.getString("ten_tuyen");
                    double doanhThu = rs.getDouble("doanh_thu");

                    list.add(new DoanhThuTuyen(tenTuyen, doanhThu));
                }
            }
        }
        return list;
    }

    public List<TyLeLapDay> getTyLeLapDay(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        List<TyLeLapDay> list = new ArrayList<>();
        String sql = "{CALL sp_ThongKeTyLeLapDay(?, ?)}";
        try (Connection conn = DBConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {
            cs.setDate(1, Date.valueOf(tuNgay));
            cs.setDate(2, Date.valueOf(denNgay));

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    String maLichTrinh = rs.getString("ma_lich_trinh");
                    String tenTau = rs.getString("ten_tau");
                    double tyLeLapDay = rs.getDouble("ty_le_lap_day");

                    list.add(new TyLeLapDay(maLichTrinh, tenTau, tyLeLapDay));
                }
            }
        }
        return list;
    }

    public List<KhachHangVIP> getKhachHangVIP(int soLuong) throws SQLException {
        List<KhachHangVIP> list = new ArrayList<>();
        String sql = "{CALL sp_ThongKeKhachHangVIP(?)}";

        try (Connection conn = DBConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, soLuong);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    String hoTen = rs.getString("ho_ten");
                    String sdt = rs.getString("sdt");
                    double tongTien = rs.getDouble("tong_tien_chi_tieu");

                    list.add(new KhachHangVIP(hoTen, sdt, tongTien));
                }
            }
        }
        return list;
    }

    public List<DoanhSo> getDoanhSo(int thang, int nam) throws SQLException {
        List<DoanhSo> list = new ArrayList<>();
        String sql = "{CALL sp_ThongKeDoanhSo(?, ?)}";

        try (Connection conn = DBConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, thang);
            cs.setInt(2, nam);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    String maNhanVien = rs.getString("ma_nhan_vien");
                    String hoTen = rs.getString("ho_ten");
                    int soVeBan = rs.getInt("so_ve_ban");
                    double doanhSo = rs.getDouble("doanh_so");

                    list.add(new DoanhSo(maNhanVien, hoTen, soVeBan, doanhSo));
                }
            }
        }
        return list;
    }

    public Map<String, String> getDashboardData() throws SQLException {
        Map<String, String> data = new HashMap<>();

        String sql = "SELECT " +
                "(SELECT COALESCE(SUM(gia_ve), 0) FROM ve_tau WHERE DATE(ngay_dat) = CURDATE()) AS doanh_thu, " +
                "(SELECT COUNT(*) FROM ve_tau WHERE DATE(ngay_dat) = CURDATE()) AS so_ve, " +
                "(SELECT COUNT(*) FROM lich_trinh WHERE ngay_di BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 3 HOUR)) AS tau_sap_chay";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    double doanhThu = rs.getDouble("doanh_thu");
                    data.put("doanhThu", String.valueOf(doanhThu));

                    int soVe = rs.getInt("so_ve");
                    data.put("soVe", String.valueOf(soVe));

                    int tauSapChay = rs.getInt("tau_sap_chay");
                    data.put("tauSapChay", String.valueOf(tauSapChay));
                }
            }
            return data;
        }
    }

    public DefaultCategoryDataset getDoanhThuBayNgay() throws SQLException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "{CALL sp_DoanhThuBayNgay}";
        try (Connection conn = DBConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    dataset.addValue(rs.getDouble("doanh_thu"), "Doanh thu", rs.getString("ngay"));
                }
            }
        }
        return dataset;
    }
}
