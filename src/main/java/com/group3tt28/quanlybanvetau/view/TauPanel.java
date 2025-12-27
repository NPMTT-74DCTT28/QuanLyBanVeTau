package com.group3tt28.quanlybanvetau.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class TauPanel extends BasePanel {
    int k = 0;
    private JTextField fieldMaTau;
    private JTextField fieldTenTau;
    private JButton buttonThem;
    private JButton buttonSua;
    private JButton buttonXoa;
    private JButton buttonReset;
    private JTable tblTau;
    private boolean isEditMode = false;

    public TauPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelHome = new JPanel();
        panelHome.setBackground(new Color(152, 251, 152));
        panelHome.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel labelHome = new JLabel("Quản lý thông tin tàu");
        labelHome.setSize(200, 80);
        labelHome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panelHome.add(labelHome);

        JPanel panelTop = new JPanel(new BorderLayout(0, 5));

        JPanel panelForm = new JPanel(new GridLayout(1, 2, 5, 5));
        panelForm.setBorder(new EmptyBorder(10, 5, 10, 5));

        fieldMaTau = new JTextField();
        panelForm.add(inputField("Mã tàu:  ", fieldMaTau));

        fieldTenTau = new JTextField();
        panelForm.add(inputField("Tên tàu: ", fieldTenTau));


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

        Object[] columns = new Object[]{"Mã tàu", "Tên tàu"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        tblTau = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblTau);
        scrollPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addButtonThemActionListener(ActionListener a) {
        buttonThem.addActionListener(a);
    }

    public void addButtonSuaActionListener(ActionListener a) {
        buttonSua.addActionListener(a);
    }

    public void addButtonXoaActionListener(ActionListener a) {
        buttonXoa.addActionListener(a);
    }

    public void addButtonResetActionListener(ActionListener a) {
        buttonReset.addActionListener(a);
    }

    public String getMaTau() {
        return fieldMaTau.getText().trim();
    }

    public void setMaTau(String maTau) {
        fieldMaTau.setText(maTau);
    }

    public String getTenTau() {
        return fieldTenTau.getText().trim();
    }

    public void setTenTau(String maTau) {
        fieldTenTau.setText(maTau);
    }

    public JTable getTable() {
        return tblTau;
    }

    public void startEditMode(){
        isEditMode = true;

        fieldMaTau.setEnabled(false);
        fieldMaTau.setBackground(new Color(200, 200 ,200));

        buttonThem.setEnabled(false);
        buttonSua.setEnabled(true);
        buttonXoa.setEnabled(true);
        buttonReset.setEnabled(true);
    }

    public void resetForm(){
        isEditMode = false;

        fieldMaTau.setEnabled(true);
        fieldMaTau.setText("");
        fieldMaTau.setBackground(Color.white);

        fieldTenTau.setText("");

        if(tblTau != null){
            tblTau.clearSelection();
        }
    }

    public void addTableMouseClickListener(MouseListener l){
        tblTau.addMouseListener(l);
    }

    static void main() {
        JFrame F = new JFrame();
        F.add(new TauPanel());
        F.setSize(800, 500);
        F.setVisible(true);
    }
}
