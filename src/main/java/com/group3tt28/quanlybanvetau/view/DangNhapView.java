/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author qphwn
 */
public final class DangNhapView extends BaseView{
    
    private JTextField txtMaNV;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap;
    private JButton btnThoat;

    public DangNhapView() {
        super("Đăng nhập");
        this.setSize(400, 200);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        initComponents();
    }
    
    @Override
    protected void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.add(new JLabel("Mã nhân viên"));
        txtMaNV = new JTextField();
        formPanel.add(txtMaNV);
        
        formPanel.add(new JLabel("Mật khẩu"));
        txtMatKhau = new JPasswordField();
        formPanel.add(txtMatKhau);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnDangNhap = new JButton("Đăng nhập");
        btnThoat = new JButton("Thoát");
        buttonPanel.add(btnDangNhap);
        buttonPanel.add(btnThoat);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        this.add(mainPanel);
    }
    
    public String getMaNV() {
        return txtMaNV.getText().trim();
    }
    
    public String getMatKhau() {
        return new String(txtMatKhau.getPassword()).trim();
    }

    public void setMaNV(String maNV) {
        txtMaNV.setText(maNV);
    }

    public void setMatKhau(String matKhau) {
        txtMatKhau.setText(matKhau);
    }
    
    public void addLoginListener(ActionListener l) {
        btnDangNhap.addActionListener(l);
    }
    
    public void addExitListener(ActionListener l) {
        btnThoat.addActionListener(l);
    }
    
    public static void main(String[] args) {
        DangNhapView view = new DangNhapView();
        view.setVisible(true);
    }
}
