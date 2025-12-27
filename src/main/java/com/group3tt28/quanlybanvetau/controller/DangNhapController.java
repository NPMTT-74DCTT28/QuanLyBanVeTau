package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.NhanVienDAO;
import com.group3tt28.quanlybanvetau.model.NhanVien;
import com.group3tt28.quanlybanvetau.util.BamMatKhau;
import com.group3tt28.quanlybanvetau.util.SessionManager;
import com.group3tt28.quanlybanvetau.view.DangNhapFrame;
import com.group3tt28.quanlybanvetau.view.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DangNhapController {

    private final DangNhapFrame view;

    private final NhanVienDAO dao;

    public DangNhapController() {
        dao = new NhanVienDAO();

        view = new DangNhapFrame();
        view.addLoginListener(new LoginListener());
        view.addExitListener(new ExitListener());

        view.setVisible(true);
    }

    private class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String maNhanVien = view.getMaNV();
            String matKhau = view.getMatKhau();

            if (maNhanVien.isEmpty() || matKhau.isEmpty()) {
                view.showWarning("Vui lòng nhập đủ mã nhân viên và mật khẩu!");
                return;
            }

            try {
                NhanVien nhanVien = dao.getNhanVienByMaNV(maNhanVien);

                if (nhanVien == null || !BamMatKhau.checkMatKhau(matKhau, nhanVien.getMatKhau())) {
                    view.showWarning("Tài khoản hoặc mật khẩu không chính xác!");
                    return;
                }

                SessionManager.setCurrentUser(nhanVien);
                new MainFrame().setVisible(true);
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                view.showError("Lỗi hệ thống: " + ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                view.showError("Lỗi không xác định: " + ex.getMessage());
            }
        }
    }

    private class ExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (view.showConfirm("Bạn muốn thoát ứng dụng?")) {
                view.dispose();
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        DangNhapController dangNhapController = new DangNhapController();
    }
}
