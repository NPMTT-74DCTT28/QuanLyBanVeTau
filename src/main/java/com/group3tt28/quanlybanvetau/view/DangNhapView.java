package com.group3tt28.quanlybanvetau.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public final class DangNhapView extends BaseView {

    private JTextField fieldMaNV;
    private JPasswordField fieldMatKhau;
    private JButton buttonDangNhap;
    private JButton buttonThoat;

    public DangNhapView() {
        super("Đăng nhập");
        this.setSize(400, 200);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        initComponents();
    }

    public static void main(String[] args) {
        DangNhapView view = new DangNhapView();
        view.setVisible(true);
    }

    @Override
    protected void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        formPanel.add(new JLabel("Mã nhân viên: "));
        fieldMaNV = new JTextField();
        formPanel.add(fieldMaNV);

        formPanel.add(new JLabel("Mật khẩu: "));
        fieldMatKhau = new JPasswordField();
        formPanel.add(fieldMatKhau);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonDangNhap = new JButton("Đăng nhập");
        buttonThoat = new JButton("Thoát");
        buttonPanel.add(buttonDangNhap);
        buttonPanel.add(buttonThoat);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel);
    }

    public String getMaNV() {
        return fieldMaNV.getText().trim();
    }

    public void setMaNV(String maNV) {
        fieldMaNV.setText(maNV);
    }

    public String getMatKhau() {
        return new String(fieldMatKhau.getPassword()).trim();
    }

    public void setMatKhau(String matKhau) {
        fieldMatKhau.setText(matKhau);
    }

    public void addLoginListener(ActionListener l) {
        buttonDangNhap.addActionListener(l);
    }

    public void addExitListener(ActionListener l) {
        buttonThoat.addActionListener(l);
    }
}
