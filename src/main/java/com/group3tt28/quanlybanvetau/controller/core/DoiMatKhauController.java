package com.group3tt28.quanlybanvetau.controller.core;

import com.group3tt28.quanlybanvetau.dao.NhanVienDAO;
import com.group3tt28.quanlybanvetau.model.NhanVien;
import com.group3tt28.quanlybanvetau.util.BamMatKhau;
import com.group3tt28.quanlybanvetau.util.SessionManager;
import com.group3tt28.quanlybanvetau.view.core.DoitMatKhauDialog;
import com.group3tt28.quanlybanvetau.view.core.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DoiMatKhauController {

    private final MainFrame parent;
    private final DoitMatKhauDialog dialog;
    private final NhanVienDAO dao;

    public DoiMatKhauController(MainFrame parent) {
        this.parent = parent;
        this.dialog = new DoitMatKhauDialog(this.parent);
        this.dao = NhanVienDAO.getInstance();

        dialog.addXacNhanListener(new XacNhanListener());
        dialog.addHuyListener(new HuyListener());

        dialog.setVisible(true);
    }

    private class XacNhanListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (dialog.thongBaoLoiDauVao() != null) {
                    JOptionPane.showMessageDialog(dialog, dialog.thongBaoLoiDauVao(), "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                NhanVien currentUser = SessionManager.getCurrentUser();
                String matKhauCu = dialog.getMatKhauCu();
                if (!BamMatKhau.checkMatKhau(matKhauCu, currentUser.getMatKhau())) {
                    JOptionPane.showMessageDialog(dialog, "Mật khẩu cũ không chính xác!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String matKhauMoi = dialog.getMatKhauMoi();
                String matKhauBam = BamMatKhau.bamMatKhau(matKhauMoi);

                if (dao.doiMatKhau(currentUser.getId(), matKhauBam)) {
                    if (JOptionPane.showConfirmDialog(dialog, "Bạn muốn đổi mật khẩu?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(dialog, "Thay đổi mật khẩu thành công, " + "vui lòng đăng nhập lại!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        SessionManager.clearSession();
                        dialog.dispose();
                        parent.dispose();
                        new DangNhapController();
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Có lỗi xảy ra khi đổi mật khẩu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Lỗi không xác định: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class HuyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dialog.dispose();
        }
    }
}
