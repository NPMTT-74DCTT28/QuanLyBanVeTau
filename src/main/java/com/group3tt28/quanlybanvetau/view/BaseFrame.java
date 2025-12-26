package com.group3tt28.quanlybanvetau.view;

import javax.swing.*;

public abstract class BaseFrame extends JFrame {

    public BaseFrame(String tieuDe) {
        setTitle(tieuDe);
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void showMessage(String thongBao) {
        JOptionPane.showMessageDialog(this, thongBao, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String thongBao) {
        JOptionPane.showMessageDialog(this, thongBao, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public boolean showConfirm(String thongBao) {
        int option = JOptionPane.showConfirmDialog(this, thongBao, "Xác nhận", JOptionPane.YES_NO_OPTION);
        return option == JOptionPane.YES_OPTION;
    }

    protected abstract void initComponents();
}
