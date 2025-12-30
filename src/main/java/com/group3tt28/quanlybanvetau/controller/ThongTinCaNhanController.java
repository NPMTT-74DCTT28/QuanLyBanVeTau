package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.NhanVienDAO;
import com.group3tt28.quanlybanvetau.model.NhanVien;
import com.group3tt28.quanlybanvetau.util.SessionManager;
import com.group3tt28.quanlybanvetau.view.frame.MainFrame;
import com.group3tt28.quanlybanvetau.view.dialog.ThongTinCaNhanDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThongTinCaNhanController {

    private final MainFrame parent;
    private final ThongTinCaNhanDialog dialog;
    private final NhanVien currentUser;
    private final NhanVienDAO dao;

    public ThongTinCaNhanController(MainFrame parent) {
        this.parent = parent;
        this.dialog = new ThongTinCaNhanDialog(this.parent);
        this.dao = new NhanVienDAO();
        this.currentUser = SessionManager.getCurrentUser();

        loadThongTin();

        this.dialog.addXacNhanListener(new XacNhanListener());
        this.dialog.addQuayLaiListener(new QuayLaiListener());

        this.dialog.setVisible(true);
    }

    private void loadThongTin() {
        if (currentUser != null) {
            dialog.setHoTen(currentUser.getHoTen());
            dialog.setNgaySinh(currentUser.getNgaySinh());
            dialog.setGioiTinh(currentUser.getGioiTinh());
            dialog.setSdt(currentUser.getSdt());
            dialog.setEmail(currentUser.getEmail());
            dialog.setDiaChi(currentUser.getDiaChi());
        }
    }

    private class XacNhanListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (dialog.thongBaoLoiDauVao() != null) {
                    dialog.showWarning(dialog.thongBaoLoiDauVao());
                    return;
                }
                currentUser.setHoTen(dialog.getHoTen());
                currentUser.setNgaySinh(dialog.getNgaySinh());
                currentUser.setGioiTinh(dialog.getGioiTinh());
                currentUser.setSdt(dialog.getSdt());
                currentUser.setEmail(dialog.getEmail());
                currentUser.setDiaChi(dialog.getDiaChi());

                if (dao.update(currentUser)) {
                    if (dialog.showConfirm("Bạn muốn cập nhật thông tin?")) {
                        dialog.showMessage("Cập nhật thông tin thành công!");
                        parent.setXinChao(currentUser.getHoTen());
                        loadThongTin();
                    }
                } else {
                    dialog.showError("Cập nhật thông tin thất bại! Vui lòng kiểm tra lại!");
                }
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                dialog.showError("Lỗi hệ thống: " + ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                dialog.showError("Lỗi không xác định: " + ex.getMessage());
            }
        }
    }

    private class QuayLaiListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (parent instanceof MainFrame) {
                dialog.dispose();
            }
        }
    }
}
