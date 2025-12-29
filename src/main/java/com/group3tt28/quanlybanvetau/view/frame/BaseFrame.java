package com.group3tt28.quanlybanvetau.view.frame;

import javax.swing.*;
import java.awt.*;

public abstract class BaseFrame extends JFrame {

    protected final Color PRIMARY_COLOR = new Color(60, 179, 113);
    protected final Color SECONDARY_COLOR = new Color(153, 204, 255);
    protected final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    protected final Font FONT_PLAIN = new Font("Segoe UI", Font.PLAIN, 14);

    public BaseFrame(String tieuDe) {
        setTitle(tieuDe);
        setSize(1280, 720);
        setBackground(Color.WHITE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void showMessage(String thongBao) {
        JOptionPane.showMessageDialog(this, thongBao, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showWarning(String thongBao) {
        JOptionPane.showMessageDialog(this, thongBao, "Cảnh báo", JOptionPane.WARNING_MESSAGE);
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
