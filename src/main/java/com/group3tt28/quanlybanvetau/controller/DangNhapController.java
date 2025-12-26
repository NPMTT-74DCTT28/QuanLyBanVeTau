package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.NhanVienDAO;
import com.group3tt28.quanlybanvetau.model.NhanVien;
import com.group3tt28.quanlybanvetau.util.BamMatKhau;
import com.group3tt28.quanlybanvetau.util.SessionManager;
import com.group3tt28.quanlybanvetau.view.DangNhapView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DangNhapController {

    private static DangNhapView view;

    public DangNhapController() {
        view = new DangNhapView();
        view.addLoginListener(new LoginListener());
        view.addExitListener(new ExitListener());

        view.setVisible(true);
    }

    private static class LoginListener implements ActionListener {

        NhanVienDAO dao = new NhanVienDAO();

        @Override
        public void actionPerformed(ActionEvent e) {
            String maNhanVien = view.getMaNV();
            String matKhau = view.getMatKhau();

            if (maNhanVien.isEmpty() || matKhau.isEmpty()) {
                view.showError("Vui lòng nhập đủ mã nhân viên và mật khẩu!");
                return;
            }

            try {
                NhanVien nhanVien = dao.getNhanVienByMaNV(maNhanVien);

                if (nhanVien == null) {
                    view.showError("Tài khoản không tồn tại");
                    return;
                }

                if (!BamMatKhau.checkMatKhau(matKhau, nhanVien.getMatKhau())) {
                    view.showError("Mật khẩu không chính xác!");
                    return;
                }

                SessionManager.setCurrentUser(nhanVien);
                view.showMessage("Đăng nhập thành công!");
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                view.showError("Lỗi hệ thống: " + ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                view.showError("Lỗi không xác định: " + ex.getMessage());
            }
        }
    }

    private static class ExitListener implements ActionListener {

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
