package com.group3tt28.quanlybanvetau.view;

import com.group3tt28.quanlybanvetau.model.GaTau;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class TuyenDuongPanel extends BaseFrame {
    private JTextField fieldMaTuyen, fieldTenTuyen, fieldKhoangcach, fieldGiaCB ;
    private JComboBox<GaTau> cboGadi, cboGaden;
    private JButton btnthem, btnsua, btnxoa;
    private DefaultTableModel model;
    private JTable table;
    public TuyenDuongPanel() {
        super("Tuyen Duong");
        this.setSize(900,500);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        initComponents();
    }

    @Override
    protected void initComponents() {
    JPanel mainPanel = new JPanel(new BorderLayout(10,10));
    mainPanel.setBorder( new EmptyBorder(10,10,10,10));
    JPanel formPanel = new JPanel(new GridLayout(6,2, 10, 10));

    formPanel.add(new JLabel("Mã tuyến đường:"));
    fieldMaTuyen = new JTextField();
    formPanel.add(fieldMaTuyen);

    formPanel.add(new JLabel("Tên tuyến đường:"));
    fieldTenTuyen = new JTextField();
    formPanel.add(fieldTenTuyen);

    cboGadi = new JComboBox<>();
    cboGaden = new JComboBox<>();
    formPanel.add(new JLabel("Ga đi:"));
    formPanel.add(cboGadi);
    formPanel.add(new JLabel("Ga đến:"));
    formPanel.add(cboGaden);

    formPanel.add(new JLabel("Khoảng cách (Km): "));
    fieldKhoangcach = new JTextField();
    formPanel.add(fieldKhoangcach);

    formPanel.add(new JLabel("Giá cơ bản: "));
    fieldGiaCB = new JTextField();
    formPanel.add(fieldGiaCB);

    mainPanel.add(formPanel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel( new FlowLayout(FlowLayout.CENTER) );
    btnthem = new JButton("Thêm");
    btnsua = new JButton("Sửa");
    btnxoa = new JButton("Xóa");
    buttonPanel.add(btnthem);
    buttonPanel.add(btnsua);
    buttonPanel.add(btnxoa);

    String[] columNames = {"Mã Tuyến","Tên Tuyến","Ga Đi","Ga Đến","Khoảng Cách (km)","Giá Cơ Bản"};
    model = new DefaultTableModel(columNames, 0);
    this.table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(new Dimension(400,200));
    JPanel southPanel = new JPanel();
    southPanel.setLayout(new BorderLayout(10, 10));
    southPanel.add(buttonPanel, BorderLayout.NORTH);
    southPanel.add(scrollPane, BorderLayout.CENTER);

    mainPanel.add(southPanel, BorderLayout.SOUTH);
    this.add(mainPanel);
    }
    public String getMaTuyen() {
        return fieldMaTuyen.getText().trim();
    }
    public void setMaTuyen(String maTuyen) {
        fieldMaTuyen.setText(maTuyen);
    }
    public String getTenTuyen() {
        return fieldTenTuyen.getText().trim();
    }
    public void setTenTuyen(String tenTuyen) {
        fieldTenTuyen.setText(tenTuyen);
    }
    public String getGadi() {
        return cboGadi.getSelectedItem().toString();
    }
    public void setGadi(String gadi) {
        cboGadi.setSelectedItem(gadi);
    }
    public String getGaden() {
        return cboGaden.getSelectedItem().toString();
    }
    public void setGaden(String gaden) {
        cboGaden.setSelectedItem(gaden);
    }
    public String getKhoangcach() {
        return fieldKhoangcach.getText().trim();
    }
    public void setKhoangcach(String khoangcach) {
        fieldKhoangcach.setText(khoangcach);
    }
    public String getGiaCB() {
        return fieldGiaCB.getText().trim();
    }
    public void setGiaCB(String giaCB) {
        fieldGiaCB.setText(giaCB);
    }
    public void addTuyen(ActionListener l) {
        btnthem.addActionListener(l);
    }
    public void editTuyen(ActionListener l) {
        btnsua.addActionListener(l);
    }
    public void removeTuyen(ActionListener l) {
        btnxoa.addActionListener(l);
    }
}
