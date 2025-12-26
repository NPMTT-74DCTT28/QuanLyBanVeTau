package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.LichTrinh;

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

    public boolean insert(LichTrinh lichTrinh) {
        //Viết code INSERT (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }

    public boolean update(LichTrinh lichTrinh) {
        //Viết code UPDATE (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }

    public boolean delete(int id) {
        //Viết code DELETE (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }

    public List<LichTrinh> getAll() {
        List<LichTrinh> list = new ArrayList<>();
        //Viết code SELECT *
        //dùng PreparedStatement + ResultSet
        return list;
    }
}
