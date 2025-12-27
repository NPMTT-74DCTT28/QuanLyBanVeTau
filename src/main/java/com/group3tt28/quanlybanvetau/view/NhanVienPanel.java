package com.group3tt28.quanlybanvetau.view;

import com.group3tt28.quanlybanvetau.enums.GioiTinh;
import com.group3tt28.quanlybanvetau.enums.VaiTro;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class NhanVienPanel extends BasePanel {

    private JTextField fieldMaNhanVien;
    private JPasswordField fieldMatKhau;
    private JTextField fieldHoTen;
    private JDateChooser chooserNgaySinh;
    private JComboBox<GioiTinh> boxGioiTinh;
    private JTextField fieldSdt;
    private JTextField fieldEmail;
    private JTextField fieldDiaChi;
    private JComboBox<VaiTro> boxVaiTro;
    private JButton buttonThem, buttonSua, buttonLuu, buttonXoa, buttonReset;
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel tableModel;

    public NhanVienPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelHome = new JPanel();
        panelHome.setBackground(new Color(152, 251, 152));
        panelHome.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel labelHome = new JLabel("Quản lý thông tin nhân viên");
        labelHome.setSize(200, 80);
        labelHome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panelHome.add(labelHome);

        JPanel panelTop = new JPanel(new BorderLayout(0, 5));

        JPanel panelForm = new JPanel(new GridLayout(3, 3, 5, 5));
        panelForm.setBorder(new EmptyBorder(10, 5, 10, 5));

        fieldMaNhanVien = new JTextField();
        panelForm.add(inputField("Mã nhân viên:  ", fieldMaNhanVien));

        fieldMatKhau = new JPasswordField();
        panelForm.add(inputField("Mật khẩu: ", fieldMatKhau));

        fieldHoTen = new JTextField();
        panelForm.add(inputField("Họ tên: ", fieldHoTen));

        chooserNgaySinh = new JDateChooser();
        panelForm.add(inputField("Ngày sinh: ", chooserNgaySinh));

        boxGioiTinh = new JComboBox<>(GioiTinh.values());
        panelForm.add(inputField("Giới tính: ", boxGioiTinh));

        fieldSdt = new JTextField();
        panelForm.add(inputField("SĐT: ", fieldSdt));

        fieldEmail = new JTextField();
        panelForm.add(inputField("eMail: ", fieldEmail));

        fieldDiaChi = new JTextField();
        panelForm.add(inputField("Địa chỉ: ", fieldDiaChi));

        boxVaiTro = new JComboBox<>(VaiTro.values());
        panelForm.add(inputField("Vai trò: ", boxVaiTro));

        buttonThem = new JButton("Thêm");
        buttonSua = new JButton("Sửa");
        buttonLuu = new JButton("Lưu");
        buttonXoa = new JButton("Xoá");
        buttonReset = new JButton("Reset");

        JButton[] buttons = {buttonThem, buttonSua, buttonLuu, buttonXoa, buttonReset};

        panelTop.add(panelHome, BorderLayout.NORTH);
        panelTop.add(panelForm);
        panelTop.add(buttonField(buttons), BorderLayout.SOUTH);

        Object[] columns = new Object[]{
                "Mã nhân viên", "Họ tên", "Ngày sinh", "Giới tính",
                "SĐT", "eMail", "Địa chỉ", "Vai trò"
        };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        scrollPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

//    static void main(String[] args) {
//        JFrame frame = new JFrame();
//        frame.setSize(1280, 720);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(new NhanVienPanel());
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
}
