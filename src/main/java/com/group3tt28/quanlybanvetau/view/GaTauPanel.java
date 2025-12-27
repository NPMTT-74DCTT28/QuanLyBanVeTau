package com.group3tt28.quanlybanvetau.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class GaTauPanel extends BaseFrame{
    private JTextField fieldMaga, fieldTenga, fieldDiachi, fieldThanhpho;
    private JButton btnthem, btnsua, btnxoa;
    public GaTauPanel() {
        super("GaTau");
        this.setSize(800, 500);
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

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        this.add(mainPanel);
    }
    public String getMaNV() {
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
