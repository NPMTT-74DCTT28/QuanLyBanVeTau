/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.NhanVien;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author qphwn
 */
public class NhanVienDAO {

    private static final String TEN_BANG = "nhan_vien";
    private static final String COT_ID = "id";
    private static final String COT_MA_NV = "ma_nhan_vien";
    private static final String COT_MAT_KHAU = "mat_khau";
    private static final String COT_HO_TEN = "ho_ten";
    private static final String COT_NGAY_SINH = "ngay_sinh";
    private static final String COT_GIOI_TINH = "gioi_tinh";
    private static final String COT_SDT = "sdt";
    private static final String COT_EMAIL = "email";
    private static final String COT_DIA_CHI = "dia_chi";
    private static final String COT_VAI_TRO = "vai_tro";
    
    public int insert(NhanVien nhanVien) {
        //Viết code INSERT (đặt trong try-cacth)
        //return ps.executeUpdate() (PreparedStatement)
        return 0;
    }
    
    public int update(NhanVien nhanVien) {
        //Viết code UPDATE (đặt trong try-cacth)
        //return ps.executeUpdate() (PreparedStatement)
        return 0;
    }
    
    public int delete(int id) {
        //Viết code DELETE (đặt trong try-cacth)
        //return ps.executeUpdate() (PreparedStatement)
        return 0;
    }
    
    public List<NhanVien> getAll() {
        List<NhanVien> list = new ArrayList<>();
        //Viết code SELECT *
        //dùng PreparedStatement + ResultSet
        return list;
    }
}
