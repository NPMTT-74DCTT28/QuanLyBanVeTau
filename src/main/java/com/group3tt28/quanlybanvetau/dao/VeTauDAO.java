package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.VeTau;

import java.util.ArrayList;
import java.util.List;

public class VeTauDAO {

    private static final String TEN_BANG = "ve_tau";
    private static final String COT_ID = "id";
    private static final String COT_MA_VE = "ma_ve";
    private static final String COT_ID_KH = "id_khach_hang";
    private static final String COT_ID_LICH_TRINH = "id_lich_trinh";
    private static final String COT_ID_GHE = "id_ghe";
    private static final String COT_ID_NHAN_VIEN = "id_nhan_vien";
    private static final String COT_NGAY_DAT = "ngay_dat";
    private static final String COT_GIA_VE = "gia_ve";
    private static final String COT_TRANG_THAI = "trang_thai";

    public boolean insert(VeTau veTau) {
        //Viết code INSERT (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }

    public boolean update(VeTau veTau) {
        //Viết code UPDATE (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }

    public boolean delete(int id) {
        //Viết code DELETE (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }

    public List<VeTau> getAll() {
        List<VeTau> list = new ArrayList<>();
        //Viết code SELECT *
        //dùng PreparedStatement + ResultSet
        return list;
    }
}
