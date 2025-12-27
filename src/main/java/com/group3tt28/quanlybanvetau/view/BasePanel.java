package com.group3tt28.quanlybanvetau.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class BasePanel extends JPanel {

    public BasePanel() {
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
        setBackground(Color.white);
    }

    public void showMessage(String thongBao) {
        JOptionPane.showMessageDialog(this, thongBao, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showWarning(String thongBao) {
        JOptionPane.showMessageDialog(this, thongBao, "Cảnh báo", JOptionPane.WARNING_MESSAGE);
    }

    public boolean showConfirm(String thongBao) {
        int option = JOptionPane.showConfirmDialog(this, thongBao, "Xác nhận", JOptionPane.YES_NO_OPTION);
        return option == JOptionPane.YES_OPTION;
    }

    public void showError(String thongBao) {
        JOptionPane.showMessageDialog(this, thongBao, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    protected JComponent inputField(String labelText, JComponent component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setPreferredSize(new Dimension(100, 30));
        panel.add(label);

        component.setPreferredSize(new Dimension(250, 40));
        component.setBackground(Color.white);
        panel.add(component);

        return panel;
    }

    protected JComponent buttonField(JButton[] buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        for (JButton button : buttons) {
            button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            button.setPreferredSize(new Dimension(100, 40));
            panel.add(button);
        }

        return panel;
    }

    protected abstract void initComponents();
}
