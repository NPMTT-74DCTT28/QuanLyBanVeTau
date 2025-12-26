package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.Tau;

import java.util.ArrayList;
import java.util.List;

public class TauDAO {

    private static final String TEN_BANG = "tau";
    private static final String COT_ID = "id";
    private static final String COT_MA_TAU = "ma_tau";
    private static final String COT_TEN_TAU = "ten_tau";

    public boolean insert(Tau tau) {
        //Viết code INSERT (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }

    public boolean update(Tau tau) {
        //Viết code UPDATE (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }

    public boolean delete(int id) {
        //Viết code DELETE (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }

    public List<Tau> getAll() {
        List<Tau> list = new ArrayList<>();
        //Viết code SELECT *
        //dùng PreparedStatement + ResultSet
        return list;
    }
}
