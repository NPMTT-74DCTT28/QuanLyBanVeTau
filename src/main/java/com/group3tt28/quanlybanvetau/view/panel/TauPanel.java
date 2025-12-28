package com.group3tt28.quanlybanvetau.view.panel;

import com.group3tt28.quanlybanvetau.model.Tau;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class TauPanel extends BasePanel {
    private JTextField fieldMaTau, fieldTenTau;
    private JButton buttonThem, buttonSua, buttonXoa, buttonReset;
    private JTable tblTau;
    private DefaultTableModel model;

    public TauPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelHome = new JPanel();
        panelHome.setBackground(new Color(152, 251, 152));
        JLabel labelHome = new JLabel("Quản lý thông tin tàu");
        labelHome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panelHome.add(labelHome);

        JPanel panelForm = new JPanel(new GridLayout(1, 2, 5, 5));
        fieldMaTau = new JTextField();
        fieldTenTau = new JTextField();
        panelForm.add(createInputField("Mã tàu: ", fieldMaTau));
        panelForm.add(createInputField("Tên tàu: ", fieldTenTau));

        buttonThem = new JButton("Thêm");
        buttonSua = new JButton("Sửa");
        buttonXoa = new JButton("Xóa");
        buttonReset = new JButton("Reset");
        buttonSua.setEnabled(false);
        buttonXoa.setEnabled(false);
        JButton[] buttons = {buttonThem, buttonSua, buttonXoa, buttonReset};

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(panelHome, BorderLayout.NORTH);
        panelTop.add(panelForm, BorderLayout.CENTER);
        panelTop.add(createButtonField(buttons), BorderLayout.SOUTH);

        model = new DefaultTableModel(new Object[]{"ID", "Mã tàu", "Tên tàu"}, 0);
        tblTau = new JTable(model);
        tblTau.removeColumn(tblTau.getColumnModel().getColumn(0)); // Ẩn cột ID

        add(panelTop, BorderLayout.NORTH);
        add(new JScrollPane(tblTau), BorderLayout.CENTER);
    }

    public void addButtonThemActionListener(ActionListener a) { buttonThem.addActionListener(a); }
    public void addButtonSuaActionListener(ActionListener a) { buttonSua.addActionListener(a); }
    public void addButtonXoaActionListener(ActionListener a) { buttonXoa.addActionListener(a); }
    public void addButtonResetActionListener(ActionListener a) { buttonReset.addActionListener(a); }
    public void addTableMouseClickListener(MouseListener l) { tblTau.addMouseListener(l); }

    public String getMaTau() { return fieldMaTau.getText().trim(); }
    public void setMaTau(String ma) { fieldMaTau.setText(ma); }
    public String getTenTau() { return fieldTenTau.getText().trim(); }
    public void setTenTau(String ten) { fieldTenTau.setText(ten); }
    public JTable getTable() { return tblTau; }

    public Tau getTauFromForm(){
        String maTau = getMaTau();
        String tenTau = getTenTau();

        return new Tau(maTau, tenTau);
    }

    public void startEditMode() {
        buttonThem.setEnabled(false);
        buttonSua.setEnabled(true);
        buttonXoa.setEnabled(true);
        buttonReset.setEnabled(true);
    }

    public void resetForm() {
        fieldMaTau.setEnabled(true);
        fieldMaTau.setText("");
        fieldMaTau.setBackground(Color.white);

        fieldTenTau.setText("");
        buttonThem.setEnabled(true);
        buttonSua.setEnabled(false);
        buttonXoa.setEnabled(false);
        buttonReset.setEnabled(true);
        if(tblTau != null){
            tblTau.clearSelection();
        }
    }
}