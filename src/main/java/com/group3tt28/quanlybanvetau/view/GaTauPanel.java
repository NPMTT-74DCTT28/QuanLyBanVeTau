package com.group3tt28.quanlybanvetau.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class GaTauPanel extends BaseFrame{
    private JTextField fieldMaga, fieldTenga, fieldDiachi, fieldThanhpho;
    private JButton btnthem, btnsua, btnxoa;
    private DefaultTableModel model;
    public GaTauPanel() {
        super("GaTau");
        this.setSize(900, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        initComponents();
    }

    @Override
    protected void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10,10));
        mainPanel.setBorder(new EmptyBorder(20,20,20,20));
        JPanel formPanel = new JPanel(new GridLayout(4,2, 10, 10));

        formPanel.add(new JLabel("Mã Ga: "));
        fieldMaga = new JTextField();
        formPanel.add(fieldMaga);

        formPanel.add(new JLabel("Tên Ga: "));
        fieldTenga = new JTextField();
        formPanel.add(fieldTenga);

        formPanel.add(new JLabel("Diachi: "));
        fieldDiachi = new JTextField();
        formPanel.add(fieldDiachi);

        formPanel.add(new JLabel("Thanh Pho:"));
        fieldThanhpho = new JTextField();
        formPanel.add(fieldThanhpho);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel( new FlowLayout(FlowLayout.CENTER) );
        btnthem = new JButton("Thêm");
        btnsua = new JButton("Sửa");
        btnxoa = new JButton("Xóa");
        buttonPanel.add(btnthem);
        buttonPanel.add(btnsua);
        buttonPanel.add(btnxoa);

        String[] columNames = {"Mã Ga", "Tên Ga", "Địa chỉ", "Thành phố"};
        model = new  DefaultTableModel(columNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400,200));

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout(10, 10));

        southPanel.add(buttonPanel, BorderLayout.NORTH);
        southPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        this.add(mainPanel);
    }
    public String getMaGa() {
        return fieldMaga.getText().trim();
    }
    public void setMaNV(String maNV) {
        fieldMaga.setText(maNV);
    }
    public String getTenga() {
        return fieldTenga.getText().trim();
    }
    public void setTenga(String tenga) {
        fieldTenga.setText(tenga);
    }
    public String getDiachi() {
        return fieldDiachi.getText().trim();
    }
    public void setDiachi(String diachi) {
        fieldDiachi.setText(diachi);
    }
    public String getThanhpho() {
        return fieldThanhpho.getText().trim();
    }
    public void setThanhpho(String thanhpho) {
        fieldThanhpho.setText(thanhpho);
    }
    public void addGaTau(ActionListener l) {
        btnthem.addActionListener(l);
    }
    public void editGaTau(ActionListener l) {
        btnsua.addActionListener(l);
    }
    public void removeGaTau(ActionListener l) {
        btnxoa.addActionListener(l);
    }
}
