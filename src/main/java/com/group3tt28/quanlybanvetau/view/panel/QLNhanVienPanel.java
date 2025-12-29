package com.group3tt28.quanlybanvetau.view.panel;

import com.group3tt28.quanlybanvetau.enums.GioiTinh;
import com.group3tt28.quanlybanvetau.enums.VaiTro;
import com.group3tt28.quanlybanvetau.model.NhanVien;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public final class QLNhanVienPanel extends BasePanel {

    private JTextField fieldMaNhanVien;
    private JPasswordField fieldMatKhau;
    private JTextField fieldHoTen;
    private JDateChooser chooserNgaySinh;
    private JComboBox<Object> boxGioiTinh;
    private JTextField fieldSdt;
    private JTextField fieldEmail;
    private JTextField fieldDiaChi;
    private JComboBox<Object> boxVaiTro;
    private JButton buttonThem, buttonSua, buttonXoa, buttonReset;
    private JTable table;
    private boolean isEditMode = false;

    public QLNhanVienPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.WHITE);

        JPanel panelTop = new JPanel(new BorderLayout(0, 5));
        panelTop.setBackground(Color.WHITE);

        JPanel panelTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelTitle.setBackground(PRIMARY_COLOR);
        JLabel labelTitle = new JLabel("QUẢN LÝ THÔNG TIN NHÂN VIÊN");
        labelTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        labelTitle.setForeground(Color.WHITE);
        panelTitle.add(labelTitle);

        JPanel panelForm = new JPanel(new GridLayout(3, 3, 5, 5));
        panelForm.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelForm.setBackground(Color.WHITE);

        fieldMaNhanVien = new JTextField();
        panelForm.add(createInputField("Mã nhân viên", fieldMaNhanVien, Color.WHITE));

        fieldMatKhau = new JPasswordField();
        panelForm.add(createInputField("Mật khẩu", fieldMatKhau, Color.WHITE));

        fieldHoTen = new JTextField();
        panelForm.add(createInputField("Họ tên", fieldHoTen, Color.WHITE));

        chooserNgaySinh = new JDateChooser();
        chooserNgaySinh.setMaxSelectableDate(new Date(System.currentTimeMillis()));
        panelForm.add(createInputField("Ngày sinh", chooserNgaySinh, Color.WHITE));

        boxGioiTinh = new JComboBox<>(createComboBoxModel(GioiTinh.values()));
        panelForm.add(createInputField("Giới tính", boxGioiTinh, Color.WHITE));

        fieldSdt = new JTextField();
        panelForm.add(createInputField("SĐT", fieldSdt, Color.WHITE));

        fieldEmail = new JTextField();
        panelForm.add(createInputField("Email", fieldEmail, Color.WHITE));

        fieldDiaChi = new JTextField();
        panelForm.add(createInputField("Địa chỉ", fieldDiaChi, Color.WHITE));

        boxVaiTro = new JComboBox<>(createComboBoxModel(VaiTro.values()));
        panelForm.add(createInputField("Vai trò", boxVaiTro, Color.WHITE));

        buttonThem = createStyledButton("Thêm", new Dimension(80, 40), PRIMARY_COLOR, Color.WHITE);
        buttonThem.setEnabled(true);
        buttonSua = createStyledButton("Sửa", new Dimension(80, 40), new Color(200, 200, 40), Color.WHITE);
        buttonSua.setEnabled(false);
        buttonXoa = createStyledButton("Xoá", new Dimension(80, 40), Color.RED, Color.white);
        buttonXoa.setEnabled(false);
        buttonReset = createStyledButton("Reset form", new Dimension(110, 40), PRIMARY_COLOR, Color.WHITE);
        buttonReset.setEnabled(true);

        JButton[] buttons = {buttonThem, buttonSua, buttonXoa, buttonReset};

        panelTop.add(panelTitle, BorderLayout.NORTH);
        panelTop.add(panelForm);
        panelTop.add(createButtonField(buttons, Color.white), BorderLayout.SOUTH);

        Object[] columns = new Object[]{"ID", "Mã nhân viên", "Họ tên", "Ngày sinh", "Giới tính", "SĐT", "Email", "Địa chỉ", "Vai trò"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        table = new JTable(tableModel);
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn columnId = columnModel.getColumn(0);
        table.removeColumn(columnId);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(SECONDARY_COLOR);
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setOpaque(false);
        tableHeader.setFont(FONT_PLAIN);

        JScrollPane scrollPane = new JScrollPane(table);

        TitledBorder tableBorder = new TitledBorder(new LineBorder(Color.LIGHT_GRAY), "Danh sách nhân viên",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, FONT_BOLD, Color.BLACK);
        scrollPane.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), tableBorder));
        scrollPane.setForeground(Color.BLACK);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setFont(FONT_PLAIN);

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelTable.setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        add(panelTop, BorderLayout.NORTH);
        add(panelTable, BorderLayout.CENTER);
    }

    public String getMaNhanVien() {
        return fieldMaNhanVien.getText().trim();
    }

    public void setMaNhanVien(String maNhanVien) {
        fieldMaNhanVien.setText(maNhanVien);
    }

    public String getMatKhau() {
        return new String(fieldMatKhau.getPassword());
    }

    public void setMatKhau(String matKhau) {
        fieldMatKhau.setText(matKhau);
    }

    public String getHoTen() {
        return fieldHoTen.getText().trim();
    }

    public void setHoTen(String hoTen) {
        fieldHoTen.setText(hoTen);
    }

    public LocalDate getNgaySinh() {
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

    public String getGioiTinh() {
        Object selectedItem = boxGioiTinh.getSelectedItem();
        if (selectedItem == null || selectedItem.toString().equalsIgnoreCase("Tất cả")) {
            return null;
        }
        return selectedItem.toString();
    }

    public void setGioiTinh(String label) {
        if (label == null) return;
        for (int i = 0; i < boxGioiTinh.getItemCount(); i++) {
            if (boxGioiTinh.getItemAt(i).toString().equals(label)) {
                boxGioiTinh.setSelectedIndex(i);
                return;
            }
        }
    }

    public String getSdt() {
        return fieldSdt.getText().trim();
    }

    public void setSdt(String sdt) {
        fieldSdt.setText(sdt);
    }

    public String getEmail() {
        return fieldEmail.getText().trim();
    }

    public void setEmail(String email) {
        fieldEmail.setText(email);
    }

    public String getDiaChi() {
        return fieldDiaChi.getText().trim();
    }

    public void setDiaChi(String diaChi) {
        fieldDiaChi.setText(diaChi);
    }

    public String getVaiTro() {
        Object selectedItem = boxVaiTro.getSelectedItem();
        if (selectedItem == null || selectedItem.toString().equalsIgnoreCase("Tất cả")) {
            return null;
        }
        return selectedItem.toString();
    }

    public void setVaiTro(String label) {
        if (label == null) return;
        for (int i = 0; i < boxVaiTro.getItemCount(); i++) {
            if (boxVaiTro.getItemAt(i).toString().equals(label)) {
                boxVaiTro.setSelectedIndex(i);
                return;
            }
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

        buttonThem.setEnabled(false);
        buttonSua.setEnabled(true);
        buttonXoa.setEnabled(true);
        buttonReset.setEnabled(true);
    }

    public void resetForm() {
        isEditMode = false;

        fieldMaNhanVien.setEnabled(true);
        fieldMaNhanVien.setText("");

        fieldMatKhau.setEnabled(true);
        fieldMatKhau.setText("");

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
        buttonXoa.setEnabled(false);
        buttonReset.setEnabled(true);

        if (table != null) {
            table.clearSelection();
        }
    }

    public void addThemNhanVienListener(ActionListener l) {
        buttonThem.addActionListener(l);
    }

    public void addSuaNhanVienListener(ActionListener l) {
        buttonSua.addActionListener(l);
    }

    public void addXoaNhanVienListener(ActionListener l) {
        buttonXoa.addActionListener(l);
    }

    public void addResetFormListener(ActionListener l) {
        buttonReset.addActionListener(l);
    }

    public void addTableMouseClickListener(MouseListener l) {
        table.addMouseListener(l);
    }
}