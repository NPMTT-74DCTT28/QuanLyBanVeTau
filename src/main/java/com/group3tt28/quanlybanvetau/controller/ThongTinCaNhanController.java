package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.NhanVienDAO;
import com.group3tt28.quanlybanvetau.model.NhanVien;
import com.group3tt28.quanlybanvetau.util.SessionManager;
import com.group3tt28.quanlybanvetau.view.frame.DangNhapFrame;
import com.group3tt28.quanlybanvetau.view.frame.MainFrame;
import com.group3tt28.quanlybanvetau.view.panel.ThongTinCaNhanPanel;
import com.group3tt28.quanlybanvetau.view.panel.TrangChu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThongTinCaNhanController {

    private MainFrame parent;
    private final ThongTinCaNhanPanel panel;
    private final NhanVien currentUser;
    private final NhanVienDAO dao;

    public ThongTinCaNhanController(MainFrame parent, ThongTinCaNhanPanel panel) {
        this.parent = parent;
        this.panel = panel;

        dao = new NhanVienDAO();

        this.currentUser = SessionManager.getCurrentUser();

        loadThongTin();

        this.panel.addXacNhanListener(new XacNhanListener());
        this.panel.addQuayLaiListener(new QuayLaiListener());

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
                    if (panel.showConfirm("Bạn muốn cập nhật thông tin?")) {
                        panel.showMessage("Cập nhật thông tin thành công!");
                        parent.setXinChao(currentUser.getHoTen());
                        loadThongTin();
                    }
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

    private class QuayLaiListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (parent instanceof MainFrame) {
                TrangChu trangChu = new TrangChu();
                ((MainFrame) parent).showPanel(trangChu);
            }
        }
    }
}
