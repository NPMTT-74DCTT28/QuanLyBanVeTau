package com.group3tt28.quanlybanvetau.view.panel;

import com.group3tt28.quanlybanvetau.enums.GioiTinh;
import com.group3tt28.quanlybanvetau.enums.VaiTro;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

public class TKNhanVienPanel extends BasePanel {

    private JTextField fieldTimKiem;
    private JDateChooser chooserNgaySinh;
    private JComboBox<GioiTinh> boxGioiTinh;
    private JComboBox<VaiTro> boxVaiTro;
    private JButton buttonTimKiem, buttonReset;
    private JTable table;

    public TKNhanVienPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout(5, 5));

        JPanel panelTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelTitle.setBackground(new Color(80, 200, 140));
        JLabel labelTitle = new JLabel("Danh sách nhân viên");
        labelTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panelTitle.add(labelTitle);

        JPanel panelTop = new JPanel(new BorderLayout(5, 5));

        JPanel panelForm = new JPanel(new GridLayout(2, 2, 5, 5));
        panelForm.setBorder(new EmptyBorder(5, 5, 5, 5));

        fieldTimKiem = new JTextField();
        panelForm.add(inputField("Tìm kiếm: ", fieldTimKiem));

        chooserNgaySinh = new JDateChooser();
        chooserNgaySinh.setMaxSelectableDate(new Date(System.currentTimeMillis()));
        panelForm.add(inputField("Ngày sinh: ", chooserNgaySinh));

        boxGioiTinh = new JComboBox<>(GioiTinh.values());
        panelForm.add(inputField("Giới tính: ", boxGioiTinh));

        boxVaiTro = new JComboBox<>(VaiTro.values());
        panelForm.add(inputField("Vai trò: ", boxVaiTro));

        buttonTimKiem = new JButton("Tìm kiếm");
        buttonReset = new JButton("Reset");
        JButton[] buttons = new JButton[]{buttonTimKiem, buttonReset};

        panelTop.add(panelTitle, BorderLayout.NORTH);
        panelTop.add(panelForm, BorderLayout.CENTER);
        panelTop.add(buttonField(buttons), BorderLayout.SOUTH);

        Object[] columns = new Object[]{
                "Mã nhân viên", "Họ tên", "Ngày sinh", "Giới tính",
                "SĐT", "e-Mail", "Địa chỉ", "Vai trò"
        };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(panelTop);
        add(scrollPane, BorderLayout.SOUTH);
    }
}
