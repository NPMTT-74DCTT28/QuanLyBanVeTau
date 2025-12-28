package com.group3tt28.quanlybanvetau.view.panel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class GhePanel extends BasePanel {
    int k = 0;
    private JTextField fieldSoGhe;
    private JComboBox<Integer> ComboBoxIDToaTau;
    private JButton buttonThem;
    private JButton buttonSua;
    private JButton buttonXoa;
    private JButton buttonReset;
    private JTable tblGhe;
    private boolean isEditMode = false;

    public GhePanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelHome = new JPanel();
        panelHome.setBackground(new Color(152, 251, 152));
        panelHome.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel labelHome = new JLabel("Quản lý thông tin ghế");
        labelHome.setSize(200, 80);
        labelHome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panelHome.add(labelHome);

        JPanel panelTop = new JPanel(new BorderLayout(0, 5));

        JPanel panelForm = new JPanel(new GridLayout(1, 2, 5, 5));
        panelForm.setBorder(new EmptyBorder(10, 5, 10, 5));

        fieldSoGhe = new JTextField();
        panelForm.add(createInputField("Số ghế:  ", fieldSoGhe));

        ComboBoxIDToaTau = new JComboBox<>();
        panelForm.add(createInputField("ID toa tàu: ", ComboBoxIDToaTau));


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
        panelTop.add(createButtonField(buttons), BorderLayout.SOUTH);

        Object[] columns = new Object[]{"Số ghế", "ID toa tàu"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        tblGhe = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblGhe);
        scrollPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    public String getSoGhe(){return fieldSoGhe.getText().trim();}
    public void setSoGhe (String soGhe){fieldSoGhe.setText(soGhe);}
    public int getIDToaTau(){
        if (ComboBoxIDToaTau.getSelectedItem() != null){
            return Integer.parseInt(ComboBoxIDToaTau.getSelectedItem().toString());
        }else
            return 0;
    }
    public void setIDToaTau (int idToaTau){ComboBoxIDToaTau.setSelectedItem(idToaTau);}

    public void startEditMode(){
        isEditMode = true;

        fieldSoGhe.setEnabled(false);
        fieldSoGhe.setBackground(new Color(200, 200, 200));

        buttonThem.setEnabled(false);
        buttonSua.setEnabled(true);
        buttonXoa.setEnabled(true);
        buttonReset.setEnabled(true);
    }

    public void reSetForm(){
        isEditMode = false;

        fieldSoGhe.setEnabled(true);
        fieldSoGhe.setText("");
        fieldSoGhe.setBackground(Color.WHITE);

        ComboBoxIDToaTau.setSelectedIndex(0);

        if(tblGhe != null){
            tblGhe.clearSelection();
        }
    }

    public void addButtonThemActionListener(ActionListener a){buttonThem.addActionListener(a);}
    public void addButtonSuaActionListener(ActionListener a){buttonSua.addActionListener(a);}
    public void addButtonXoaActionListener(ActionListener a){buttonXoa.addActionListener(a);}
    public void addButtonResetActionListener(ActionListener a ){buttonReset.addActionListener(a);}
    public void addTableMouseClickListener(MouseListener l){
        tblGhe.addMouseListener(l);
    }

    static void main() {
        JFrame F = new JFrame();
        F.add(new GhePanel());
        F.setSize(800, 500);
        F.setVisible(true);
    }
}
