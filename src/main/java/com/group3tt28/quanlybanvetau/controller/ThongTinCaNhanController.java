package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.NhanVienDAO;
import com.group3tt28.quanlybanvetau.model.NhanVien;
import com.group3tt28.quanlybanvetau.util.SessionManager;
import com.group3tt28.quanlybanvetau.view.panel.ThongTinCaNhanPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThongTinCaNhanController {

    private final ThongTinCaNhanPanel panel;
    private final NhanVien currentUser;
    private final NhanVienDAO dao;

    public ThongTinCaNhanController(ThongTinCaNhanPanel panel) {
        this.panel = panel;

        dao = new NhanVienDAO();
        this.currentUser = SessionManager.getCurrentUser();
        loadThongTin();

        this.panel.addXacNhanListener(new XacNhanListener());
        this.panel.setVisible(true);
    }

    private void loadThongTin() {
        if (currentUser != null) {
            panel.setHoTen(currentUser.getHoTen());
            panel.setNgaySinh(currentUser.getNgaySinh());
            panel.setGioiTinh(currentUser.getGioiTinh());
            panel.setSdt(currentUser.getSdt());
            panel.setEmail(currentUser.getEmail());
            panel.setDiaChi(currentUser.getDiaChi());
        }
    }

    private class XacNhanListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (panel.thongBaoLoiDauVao() != null) {
                    panel.showWarning(panel.thongBaoLoiDauVao());
                    return;
                }
                currentUser.setHoTen(panel.getHoTen());
                currentUser.setNgaySinh(panel.getNgaySinh());
                currentUser.setGioiTinh(panel.getGioiTinh());
                currentUser.setSdt(panel.getSdt());
                currentUser.setEmail(panel.getEmail());
                currentUser.setDiaChi(panel.getDiaChi());

                if (dao.update(currentUser)) {
                    SessionManager.clearSession();
                    SessionManager.startSession(currentUser);
                    panel.showMessage("Cập nhật thông tin thành công!");
                } else {
                    panel.showError("Cập nhật thông tin thất bại! Vui lòng kiểm tra lại!");
                }
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi không xác định: " + ex.getMessage());
            }
        }
    }
}
