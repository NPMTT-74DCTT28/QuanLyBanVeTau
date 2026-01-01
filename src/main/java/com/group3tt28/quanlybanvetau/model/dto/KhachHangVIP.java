package com.group3tt28.quanlybanvetau.model.dto;

public class KhachHangVIP {

    private String hoTen;
    private String sdt;
    private double tongTienChiTieu;

    public KhachHangVIP(String hoTen, String sdt, double tongTienChiTieu) {
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.tongTienChiTieu = tongTienChiTieu;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public double getTongTienChiTieu() {
        return tongTienChiTieu;
    }
}
