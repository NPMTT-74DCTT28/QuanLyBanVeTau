package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.dto.*;
import com.group3tt28.quanlybanvetau.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
                    int maLichTrinh = rs.getInt("ma_lic_trinh");
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
        String sql = "{CALL sp_KhachHangVIP(?)}";

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

    public List<DoanhSoNhanVien> getDoanhSoNhanVien(int thang, int nam) throws SQLException {
        List<DoanhSoNhanVien> list = new ArrayList<>();
        String sql = "{CALL sp_ThongKeDoanhSoNhanVien(?, ?)}";

        try (Connection conn = DBConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, thang);
            cs.setInt(2, nam);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    String hoTen = rs.getString("ho_ten");
                    double doanhSo = rs.getDouble("doanh_so");

                    list.add(new DoanhSoNhanVien(hoTen, doanhSo));
                }
            }
        }
        return list;
    }
}
