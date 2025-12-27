package com.group3tt28.quanlybanvetau.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class LoaiToaPanel extends BasePanel{
    int k = 0;
    private JTextField fieldTenLoai;
    private JTextField fieldHeSoGia;
    private JButton buttonThem;
    private JButton buttonSua;
    private JButton buttonXoa;
    private  JButton buttonReset;
    private JTable tblLoaiToa;
    private boolean isEditMode = false;

    public LoaiToaPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelHome = new JPanel();
        panelHome.setBackground(new Color(152, 251, 152));
        panelHome.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel labelHome = new JLabel("Quản lý thông tin loại toa");
        labelHome.setSize(200, 80);
        labelHome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panelHome.add(labelHome);

        JPanel panelTop = new JPanel(new BorderLayout(0, 5));

        JPanel panelForm = new JPanel(new GridLayout(1, 2, 5, 5));
        panelForm.setBorder(new EmptyBorder(10, 5, 10, 5));

        fieldTenLoai = new JTextField();
        panelForm.add(inputField("Tên loại toa:  ", fieldTenLoai));

        fieldHeSoGia = new JTextField();
        panelForm.add(inputField("Hệ số giá: ", fieldHeSoGia));


        buttonThem = new JButton("Thêm");
        buttonThem.setEnabled(true);
        buttonSua = new JButton("Sửa");
        buttonSua.setEnabled(false);
        buttonXoa = new JButton("Xoá");
        buttonXoa.setEnabled(false);
        buttonReset = new JButton("Reset");
        buttonReset.setEnabled(true);

        JButton[] buttons = {buttonThem, buttonSua, buttonXoa, buttonReset};

        panelTop.add(panelHome, BorderLayout.NORTH);
        panelTop.add(panelForm);
        panelTop.add(buttonField(buttons), BorderLayout.SOUTH);

        Object[] columns = new Object[]{"Tên loại toa", "Hệ số giá"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        tblLoaiToa = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblLoaiToa);
        scrollPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    public  void setButtonThemActionListener(ActionListener a){
        buttonThem.addActionListener(a);
    }
    public  void setButtonSuaActionListener(ActionListener a){
        buttonSua.addActionListener(a);
    }
    public  void setButtonXoaActionListener(ActionListener a){
        buttonXoa.addActionListener(a);
    }
    public  void setButtonResetActionListener(ActionListener a){
        buttonReset.addActionListener(a);
    }

    public String getTenLoai(){
        return fieldTenLoai.getText().trim();
    }
    public void setTenLoai(String maTau){
        fieldHeSoGia.setText(maTau);
    }
    public String getHeSoGia(){
        return fieldHeSoGia.getText().trim();
    }
    public void setHeSoGia(String maTau){
        fieldHeSoGia.setText(maTau);
    }
    public JTable getTable() {
        return tblLoaiToa;
    }

    public void startEditMode(){
        isEditMode = true;

        fieldTenLoai.setEnabled(false);
        fieldTenLoai.setBackground(new Color(200, 200 ,200));

        buttonThem.setEnabled(false);
        buttonSua.setEnabled(true);
        buttonXoa.setEnabled(true);
        buttonReset.setEnabled(true);
    }

    public void resetForm(){
        isEditMode = false;

        fieldTenLoai.setEnabled(true);
        fieldTenLoai.setText("");
        fieldTenLoai.setBackground(Color.white);

        fieldHeSoGia.setText("");

        if(tblLoaiToa != null){
            tblLoaiToa.clearSelection();
        }
    }

    public void addTableMouseClickListener(MouseListener l){
        tblLoaiToa.addMouseListener(l);
    }

    public static void main(String[] args) {
        JFrame F = new JFrame();
        F.add(new LoaiToaPanel());
        F.setSize(800, 500);
        F.setVisible(true);
    }
}
