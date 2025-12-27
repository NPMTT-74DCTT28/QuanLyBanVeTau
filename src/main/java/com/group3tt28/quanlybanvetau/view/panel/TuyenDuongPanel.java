package com.group3tt28.quanlybanvetau.view.panel;

import com.group3tt28.quanlybanvetau.model.GaTau;
import com.group3tt28.quanlybanvetau.model.TuyenDuong;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

public final class TuyenDuongPanel extends BasePanel {
    private JTextField fieldMaTuyen, fieldTenTuyen, fieldKhoangcach, fieldGiaCB ;
    private JComboBox<Object> cboGadi, cboGaden;
    private JButton btnthem, btnsua, btnxoa, btnreset;
    private DefaultTableModel model;
    private JTable table;
    public boolean isEditMode = false;
    public TuyenDuongPanel() {
        initComponents();
    }
    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(152, 251, 152));
        titlePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel title = new JLabel("Tuyen Duong");
        title.setSize(new Dimension(200, 80));
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titlePanel.add(title);

        JPanel panelTop = new JPanel(new BorderLayout(0,5));
        JPanel panelFrom = new JPanel(new GridLayout(3,2, 5,5));
        panelFrom.setBorder(new EmptyBorder(5, 5, 5, 5));

        fieldMaTuyen = new JTextField();
        panelFrom.add(inputField("Mã tuyến: ", fieldMaTuyen));

        fieldTenTuyen = new JTextField();
        panelFrom.add(inputField("Tên tuyến: ", fieldTenTuyen));

        cboGadi = new JComboBox<>();
        panelFrom.add(inputField("Ga đi: ", cboGadi));

        fieldKhoangcach = new JTextField();
        onlyNumber(fieldKhoangcach);
        panelFrom.add(inputField("Khoảng cách (Km): ", fieldKhoangcach));

        cboGaden = new JComboBox<>();
        panelFrom.add(inputField("Ga đến: ", cboGaden));

        fieldGiaCB = new JTextField();
        onlyNumber(fieldGiaCB);
        panelFrom.add(inputField("Giá cơ bản: ", fieldGiaCB));

        btnthem = new JButton("Thêm");
        btnsua = new JButton("Sửa");
        btnxoa = new JButton("Xóa");
        btnreset = new JButton("Reset");
        JButton[] btn =  {btnthem, btnsua, btnxoa, btnreset};

        panelTop.add(titlePanel, BorderLayout.NORTH);
        panelTop.add(panelFrom);
        panelTop.add(buttonField(btn), BorderLayout.SOUTH);

        String[] columNames = {"Mã Tuyến","Tên Tuyến","Ga Đi","Ga Đến","Khoảng Cách (km)","Giá Cơ Bản"};
        model = new DefaultTableModel(columNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setFont(new Font("Segoe UI", Font.BOLD, 20));
        scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

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
        Object selected = cboGadi.getSelectedItem();
        return (selected != null) ? selected.toString() : "__Chọn Ga Đi__";
    }
    public void setGadi(int idgadi) {
        for (int i = 0; i < cboGadi.getItemCount(); i++) {
            GaTau ga = (GaTau) cboGadi.getItemAt(i);
            if (ga.getId() == idgadi) {
                cboGadi.setSelectedIndex(i);
                break;
            }
        }
    }
    public String getGaden() {
        Object selected = cboGaden.getSelectedItem();
        return (selected != null) ? selected.toString() : "__Chọn Ga Đến__";
    }
    public void setGaden(int idgaden) {
        for (int i = 0; i < cboGaden.getItemCount(); i++) {
            GaTau ga = (GaTau) cboGaden.getItemAt(i);
            if (ga.getId() == idgaden) {
                cboGaden.setSelectedIndex(i);
                break;
            }
        }
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
    public TuyenDuong getTuyenDuong() {
        String maTuyen = getMaTuyen();
        String tenTuyen = getTenTuyen();
        GaTau gaDi = (GaTau) cboGadi.getSelectedItem();
        GaTau gaDen = (GaTau) cboGaden.getSelectedItem();
        int idgadi = gaDi.getId();
        int idgaden = gaDen.getId();
        Double khoangcach = Double.parseDouble(getKhoangcach());
        Double giaCB = Double.parseDouble(getGiaCB());
        return new TuyenDuong(maTuyen,tenTuyen,idgadi,idgaden,khoangcach, giaCB);
    }
    public void setGatau(List<GaTau> list){
        cboGadi.removeAllItems();
        cboGaden.removeAllItems();
        for (GaTau gaTau: list){
            cboGadi.addItem(gaTau);
            cboGaden.addItem(gaTau);
        }
    }
    private void onlyNumber(JTextField field) {
        AbstractDocument doc = (AbstractDocument) field.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text.matches("[0-9.]*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
    public JTable getTable() {
        return table;
    }
    public void startEditMode(){
        isEditMode=true;
        fieldMaTuyen.setEnabled(false);
        fieldTenTuyen.setEnabled(true);
        cboGadi.setEnabled(true);
        cboGaden.setEnabled(true);
        fieldKhoangcach.setEnabled(true);
        fieldGiaCB.setEnabled(true);
        btnthem.setEnabled(false);
        btnsua.setEnabled(true);
        btnxoa.setEnabled(true);
        btnreset.setEnabled(true);
    }
    public void resetForm(){
        isEditMode=false;
        fieldMaTuyen.setEnabled(true);
        fieldMaTuyen.setText("");
        fieldTenTuyen.setEnabled(true);
        fieldTenTuyen.setText("");
        cboGadi.setEnabled(true);
        cboGadi.setSelectedItem(null);
        cboGaden.setEnabled(true);
        cboGaden.setSelectedItem(null);
        fieldKhoangcach.setEnabled(true);
        fieldKhoangcach.setText("");
        fieldGiaCB.setEnabled(true);
        fieldGiaCB.setText("");
        btnthem.setEnabled(true);
    }
    public void AddTuyen(ActionListener l) {
        btnthem.addActionListener(l);
    }
    public void EditTuyen(ActionListener l) {
        btnsua.addActionListener(l);
    }
    public void RemoveTuyen(ActionListener l) {
        btnxoa.addActionListener(l);
    }
    public void ResetTuyen(ActionListener l) {
        btnreset.addActionListener(l);
    }
    public void TableMouseClickListener(MouseListener l) {
        table.addMouseListener(l);
    }
}
