package com.group3tt28.quanlybanvetau.view;

import com.group3tt28.quanlybanvetau.enums.GioiTinh;
import com.group3tt28.quanlybanvetau.enums.VaiTro;
import com.group3tt28.quanlybanvetau.model.NhanVien;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public final class QLNhanVienPanel extends BasePanel {

    private JTextField fieldMaNhanVien;
    private JPasswordField fieldMatKhau;
    private JTextField fieldHoTen;
    private JDateChooser chooserNgaySinh;
    private JComboBox<GioiTinh> boxGioiTinh;
    private JTextField fieldSdt;
    private JTextField fieldEmail;
    private JTextField fieldDiaChi;
    private JComboBox<VaiTro> boxVaiTro;
    private JButton buttonThem, buttonSua, buttonLuu, buttonXoa, buttonReset;
    private JTable table;
    private boolean isEditMode = false;

    public QLNhanVienPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelHome = new JPanel();
        panelHome.setBackground(new Color(152, 251, 152));
        panelHome.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel labelHome = new JLabel("Quản lý thông tin nhân viên");
        labelHome.setSize(200, 80);
        labelHome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panelHome.add(labelHome);

        JPanel panelTop = new JPanel(new BorderLayout(0, 5));

        JPanel panelForm = new JPanel(new GridLayout(3, 3, 5, 5));
        panelForm.setBorder(new EmptyBorder(10, 5, 10, 5));

        fieldMaNhanVien = new JTextField();
        panelForm.add(inputField("Mã nhân viên:  ", fieldMaNhanVien));

        fieldMatKhau = new JPasswordField();
        panelForm.add(inputField("Mật khẩu: ", fieldMatKhau));

        fieldHoTen = new JTextField();
        panelForm.add(inputField("Họ tên: ", fieldHoTen));

        chooserNgaySinh = new JDateChooser();
        panelForm.add(inputField("Ngày sinh: ", chooserNgaySinh));

        boxGioiTinh = new JComboBox<>(GioiTinh.values());
        panelForm.add(inputField("Giới tính: ", boxGioiTinh));

        fieldSdt = new JTextField();
        panelForm.add(inputField("SĐT: ", fieldSdt));

        fieldEmail = new JTextField();
        panelForm.add(inputField("eMail: ", fieldEmail));

        fieldDiaChi = new JTextField();
        panelForm.add(inputField("Địa chỉ: ", fieldDiaChi));

        boxVaiTro = new JComboBox<>(VaiTro.values());
        panelForm.add(inputField("Vai trò: ", boxVaiTro));

        buttonThem = new JButton("Thêm");
        buttonThem.setEnabled(true);
        buttonSua = new JButton("Sửa");
        buttonSua.setEnabled(false);
        buttonLuu = new JButton("Lưu");
        buttonLuu.setEnabled(false);
        buttonXoa = new JButton("Xoá");
        buttonXoa.setEnabled(false);
        buttonReset = new JButton("Reset");
        buttonReset.setEnabled(true);

        JButton[] buttons = {buttonThem, buttonSua, buttonLuu, buttonXoa, buttonReset};

        panelTop.add(panelHome, BorderLayout.NORTH);
        panelTop.add(panelForm);
        panelTop.add(buttonField(buttons), BorderLayout.SOUTH);

        Object[] columns = new Object[]{"Mã nhân viên", "Họ tên", "Ngày sinh", "Giới tính", "SĐT", "eMail", "Địa chỉ", "Vai trò"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private String getMaNhanVien() {
        return fieldMaNhanVien.getText().trim();
    }

    public void setMaNhanVien(String maNhanVien) {
        fieldMaNhanVien.setText(maNhanVien);
    }

    private String getMatKhau() {
        return new String(fieldMatKhau.getPassword());
    }

    public void setMatKhau(String matKhau) {
        fieldMatKhau.setText(matKhau);
    }

    private String getHoTen() {
        return fieldHoTen.getText().trim();
    }

    public void setHoTen(String hoTen) {
        fieldHoTen.setText(hoTen);
    }

    private LocalDate getNgaySinh() {
        if (chooserNgaySinh.getDate() == null) {
            return null;
        }
        return chooserNgaySinh.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void setNgaySinh(LocalDate localDate) {
        if (localDate == null) {
            chooserNgaySinh.setDate(null);
        } else {
            chooserNgaySinh.setDate(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
    }

    private String getGioiTinh() {
        return String.valueOf(boxGioiTinh.getSelectedItem());
    }

    public void setGioiTinh(String gioiTinh) {
        try {
            boxGioiTinh.setSelectedItem(GioiTinh.valueOf(gioiTinh));
        } catch (IllegalArgumentException e) {
            boxGioiTinh.setSelectedIndex(0);
        }
    }

    private String getSdt() {
        return fieldSdt.getText().trim();
    }

    public void setSdt(String sdt) {
        fieldSdt.setText(sdt);
    }

    private String getEmail() {
        return fieldEmail.getText().trim();
    }

    public void setEmail(String email) {
        fieldEmail.setText(email);
    }

    private String getDiaChi() {
        return fieldDiaChi.getText().trim();
    }

    public void setDiaChi(String diaChi) {
        fieldDiaChi.setText(diaChi);
    }

    private String getVaiTro() {
        return String.valueOf(boxVaiTro.getSelectedItem());
    }

    public void setVaiTro(String vaiTro) {
        try {
            boxVaiTro.setSelectedItem(VaiTro.valueOf(vaiTro));
        } catch (IllegalArgumentException e) {
            boxVaiTro.setSelectedIndex(0);
        }
    }

    public JTable getTable() {
        return table;
    }

    public NhanVien getNhanVienFromForm() {
        String maNhanVien = getMaNhanVien();
        String matKhau = getMatKhau();
        String hoTen = getHoTen();
        LocalDate ngaySinh = getNgaySinh();
        String gioiTinh = getGioiTinh();
        String sdt = getSdt();
        String email = getEmail();
        String diaChi = getDiaChi();
        String vaiTro = getVaiTro();

        if (ngaySinh == null) {
            throw new IllegalArgumentException("Vui lòng chọn ngày sinh!");
        }

        if (isEditMode) {
            return new NhanVien(maNhanVien, hoTen, ngaySinh, gioiTinh, sdt, email, diaChi, vaiTro);
        } else {
            return new NhanVien(maNhanVien, matKhau, hoTen, ngaySinh, gioiTinh, sdt, email, diaChi, vaiTro);
        }
    }

    public void startEditMode() {
        isEditMode = true;

        fieldMaNhanVien.setEnabled(false);

        fieldMatKhau.setEnabled(false);
        fieldMatKhau.setText("");
        fieldMatKhau.setBackground(new Color(240, 240, 240));

        buttonThem.setEnabled(false);
        buttonSua.setEnabled(true);
        buttonLuu.setEnabled(true);
        buttonXoa.setEnabled(true);
        buttonReset.setEnabled(true);
    }

    public void resetForm() {
        isEditMode = false;

        fieldMaNhanVien.setEnabled(true);
        fieldMaNhanVien.setText("");

        fieldMatKhau.setEnabled(true);
        fieldMatKhau.setText("");
        fieldMatKhau.setBackground(Color.WHITE);

        fieldHoTen.setText("");
        chooserNgaySinh.setDate(null);
        if (boxGioiTinh.getItemCount() > 0) {
            boxGioiTinh.setSelectedIndex(0);
        }
        fieldSdt.setText("");
        fieldEmail.setText("");
        fieldDiaChi.setText("");
        if (boxVaiTro.getItemCount() > 0) {
            boxVaiTro.setSelectedIndex(0);
        }

        buttonThem.setEnabled(true);
        buttonSua.setEnabled(false);
        buttonLuu.setEnabled(false);
        buttonXoa.setEnabled(false);
        buttonReset.setEnabled(true);

        if (table != null) {
            table.clearSelection();
        }
    }

    public void addThemListener(ActionListener l) {
        buttonThem.addActionListener(l);
    }

    public void addSuaListener(ActionListener l) {
        buttonSua.addActionListener(l);
    }

    public void addLuuListener(ActionListener l) {
        buttonLuu.addActionListener(l);
    }

    public void addXoaListener(ActionListener l) {
        buttonXoa.addActionListener(l);
    }

    public void addResetListener(ActionListener l) {
        buttonReset.addActionListener(l);
    }
}