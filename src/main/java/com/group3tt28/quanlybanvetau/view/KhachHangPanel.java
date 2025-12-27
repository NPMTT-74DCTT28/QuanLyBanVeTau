package com.group3tt28.quanlybanvetau.view;

import com.group3tt28.quanlybanvetau.enums.GioiTinh;
import com.group3tt28.quanlybanvetau.model.KhachHang;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class KhachHangPanel extends BasePanel {

    private JTextField fieldCCCD;
    private JTextField fieldHoTen;
    private JDateChooser chooserNgaySinh;
    private JComboBox<GioiTinh> boxGioiTinh;
    private JTextField fieldSDT;
    private JTextField fieldDiaChi;
    private JButton buttonThem, buttonSua, buttonXoa, buttonReset;
    private JTable table;

    public KhachHangPanel(){
        initComponents();
    }
    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelTitle = new JPanel();
        panelTitle.setBackground(new Color(173,255,47));
        panelTitle.setBorder(new EmptyBorder(5,5,5,5));
        JLabel lableTitle = new JLabel("Quản lý thông tin khách hàng");
        lableTitle.setSize(200,80);
        lableTitle.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panelTitle.add(lableTitle);

        JPanel panelTop = new JPanel(new BorderLayout(5, 5 ));

        JPanel panelForm = new JPanel(new GridLayout(2,3));

        fieldCCCD = new JTextField();
        panelForm.add(inputField("CCCD: ", fieldCCCD));

        fieldHoTen = new JTextField();
        panelForm.add(inputField("Họ tên: ", fieldHoTen));

        chooserNgaySinh = new JDateChooser();
        chooserNgaySinh.setMaxSelectableDate(new Date(System.currentTimeMillis()));
        panelForm.add(inputField("Ngày sinh: ", chooserNgaySinh));

        boxGioiTinh = new JComboBox<>(GioiTinh.values());
        panelForm.add(inputField("Giới tính: ", boxGioiTinh));

        fieldSDT = new JTextField();
        panelForm.add(inputField("SĐT: ", fieldSDT));

        fieldDiaChi = new JTextField();
        panelForm.add(inputField("Địa chỉ: ", fieldDiaChi));

        buttonThem = new JButton("Thêm");
        buttonThem.setEnabled(true);
        buttonSua = new JButton("Sửa");
        buttonSua.setEnabled(false);
        buttonXoa = new JButton("Xóa");
        buttonXoa.setEnabled(false);
        buttonReset = new JButton("Reset");
        buttonReset.setEnabled(true);

        JButton[] buttons = {buttonThem, buttonSua, buttonXoa, buttonReset};

        panelTop.add(panelTitle, BorderLayout.NORTH);
        panelTop.add(panelForm);
        panelTop.add(buttonField(buttons), BorderLayout.SOUTH);

        Object[] columns = new Object[]{"CCCD", "Họ tên", "Ngày sinh", "Giới tinh", "SĐT", "Địa chỉ"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setFont(new Font("Times New Roman", Font.BOLD,13));
        scrollPane.setBorder(new EmptyBorder(10,10,10,10));

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public String getCCCD() {return fieldCCCD.getText().trim();}

    public void setCCCD(String CCCD) {fieldCCCD.setText(CCCD);}

    public   String getHoTen() {return fieldHoTen.getText().trim();}

    public void setHoTen(String hoTen) {fieldHoTen.setText(hoTen);}

    public LocalDate getNgaySinh() {
        if (chooserNgaySinh.getDate() == null) {
            return null;
        }
        return chooserNgaySinh.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void setNgaySinh(LocalDate localDate) {
        if (localDate == null){
            chooserNgaySinh.setDate(null);
        }else {
            chooserNgaySinh.setDate(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
    }

    public String getGioiTinh() {return String.valueOf(boxGioiTinh.getSelectedItem());}

    public void setGioiTinh(String label){
        if (label == null) return;
        for (int i = 0; i<boxGioiTinh.getItemCount(); i++){
            if (boxGioiTinh.getItemAt(i).toString().equals(label)){
                boxGioiTinh.setSelectedIndex(i);
                return;
            }
        }
    }

    public String getSdt() {return fieldSDT.getText().trim();}

    public void setSdt(String sdt) {fieldSDT.setText(sdt);}

    public String getDiachi() {return fieldDiaChi.getText().trim();}

    public void setDiaChi(String diaChi) {fieldDiaChi.setText(diaChi);}

    public JTable getTable() {return table;}

    public KhachHang getKhachHangFromForm() {
        String cccd = getCCCD();
        String hoTen = getHoTen();
        LocalDate ngaySinh = getNgaySinh();
        String gioiTinh = getGioiTinh();
        String sdt = getSdt();
        String diaChi = getDiachi();

        return new KhachHang(cccd, hoTen, ngaySinh, gioiTinh, sdt, diaChi);
    }

    public  void startEditMode(){
        fieldCCCD.setEnabled(false);

        buttonThem.setEnabled(false);
        buttonSua.setEnabled(true);
        buttonXoa.setEnabled(true);
        buttonReset.setEnabled(true);
    }

    public void resetForm(){
        fieldCCCD.setEnabled(true);
        fieldCCCD.setText("");

        fieldHoTen.setText("");

        chooserNgaySinh.setDate(null);

        if (boxGioiTinh.getItemCount() > 0){
            boxGioiTinh.setSelectedIndex(0);
        }

        fieldSDT.setText("");

        fieldDiaChi.setText("");

        buttonThem.setEnabled(true);
        buttonSua.setEnabled(false);
        buttonXoa.setEnabled(false);
        buttonReset.setEnabled(true);

        if (table != null) {
            table.clearSelection();
        }
    }

    public void addThemKhachHangListener(ActionListener l) {buttonThem.addActionListener(l);}

    public void addSuaKhachHangListener(ActionListener l) {buttonSua.addActionListener(l);}

    public void addXoaKhachHangListener(ActionListener l) {buttonXoa.addActionListener(l);}

    public void addResetFormListener(ActionListener l) {buttonReset.addActionListener(l);}

    public void addTableMouseClickListener(MouseListener l) {table.addMouseListener(l);}
}
