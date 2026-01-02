package com.group3tt28.quanlybanvetau.controller.core;

import com.group3tt28.quanlybanvetau.dao.NhanVienDAO;
import com.group3tt28.quanlybanvetau.model.NhanVien;
import com.group3tt28.quanlybanvetau.util.BamMatKhau;
import com.group3tt28.quanlybanvetau.util.SessionManager;
import com.group3tt28.quanlybanvetau.view.core.DangNhapFrame;
import com.group3tt28.quanlybanvetau.view.core.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class DangNhapController {

    private final DangNhapFrame frame;

    private final NhanVienDAO dao;

    public DangNhapController() {
        dao = NhanVienDAO.getInstance();

        frame = new DangNhapFrame();
        frame.addLoginListener(new LoginListener());
        frame.addExitListener(new ExitListener());
        frame.addWindowCloseListener(new WindowCloseListener());

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new DangNhapController();
    }

    private class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String maNhanVien = frame.getMaNV();
            String matKhau = frame.getMatKhau();

            if (maNhanVien.isEmpty() || matKhau.isEmpty()) {
                frame.showWarning("Vui lòng nhập đủ mã nhân viên và mật khẩu!");
                return;
            }

            try {
                NhanVien nhanVien = dao.getNhanVienByMaNV(maNhanVien);

                if (nhanVien == null || !BamMatKhau.checkMatKhau(matKhau, nhanVien.getMatKhau())) {
                    frame.showWarning("Tài khoản hoặc mật khẩu không chính xác!");
                    return;
                }

                SessionManager.startSession(nhanVien);
                MainFrame mainFrame = new MainFrame();
                new MainController(mainFrame);
                frame.dispose();
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                frame.showError("Xảy ra lỗi khi kiểm tra mật khẩu: " + ex.getMessage());
            } catch (SQLException ex) {
                ex.printStackTrace();
                frame.showError("Xảy ra lỗi khi lấy thông tin: " + ex.getMessage());
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                frame.showError("Lỗi hệ thống: " + ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                frame.showError("Xảy ra lỗi không xác định: " + ex.getMessage());
            }
        }
    }

    private class ExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (frame.showConfirm("Bạn chắc chắn muốn thoát ứng dụng?")) {
                frame.dispose();
                System.exit(0);
            }
        }
    }

    private class WindowCloseListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            if (frame.showConfirm("Bạn chắc chắn muốn thoát ứng dụng?")) {
                frame.dispose();
                System.exit(0);
            }
        }
    }
}
