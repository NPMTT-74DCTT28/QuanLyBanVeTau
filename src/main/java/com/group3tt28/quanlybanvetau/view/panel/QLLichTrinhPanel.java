package com.group3tt28.quanlybanvetau.view.panel;

import com.group3tt28.quanlybanvetau.enums.TrangThaiLichTrinh;
import com.group3tt28.quanlybanvetau.model.LichTrinh;
import com.group3tt28.quanlybanvetau.model.Tau;
import com.group3tt28.quanlybanvetau.model.TuyenDuong;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public final class QLLichTrinhPanel extends BasePanel {

    private JTextField fieldMaLichTrinh;
    private JComboBox<Tau> boxTau;
    private JComboBox<TuyenDuong> boxTuyenDuong;
    private JSpinner spinnerNgayDi;
    private JSpinner spinnerNgayDen;
    private JComboBox<TrangThaiLichTrinh> boxTrangThai;

    // Đã bỏ buttonLuu
    private JButton buttonThem, buttonSua, buttonXoa, buttonReset;
    private JTable table;

    private boolean isEditMode = false;

    public QLLichTrinhPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelHome = new JPanel();
        panelHome.setBackground(new Color(152, 251, 152));
        panelHome.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel labelHome = new JLabel("Quản lý lịch trình tàu");
        labelHome.setSize(200, 80);
        labelHome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panelHome.add(labelHome);

        JPanel panelTop = new JPanel(new BorderLayout(0, 5));

        JPanel panelForm = new JPanel(new GridLayout(3, 3, 5, 5));
        panelForm.setBorder(new EmptyBorder(10, 5, 10, 5));

        fieldMaLichTrinh = new JTextField();
        panelForm.add(createInputField("Mã lịch trình:", fieldMaLichTrinh));

        boxTau = new JComboBox<>();
        panelForm.add(createInputField("Tàu:", boxTau));

        boxTuyenDuong = new JComboBox<>();
        panelForm.add(createInputField("Tuyến đường:", boxTuyenDuong));

        // Cấu hình Spinner NGÀY ĐI
        SpinnerDateModel modelDi = new SpinnerDateModel();
        spinnerNgayDi = new JSpinner(modelDi);
        JSpinner.DateEditor editorDi = new JSpinner.DateEditor(spinnerNgayDi, "yyyy-MM-dd HH:mm:ss");
        spinnerNgayDi.setEditor(editorDi);
        spinnerNgayDi.setValue(new Date());
        panelForm.add(createInputField("Ngày đi:", spinnerNgayDi));

        // Cấu hình Spinner NGÀY ĐẾN
        SpinnerDateModel modelDen = new SpinnerDateModel();
        spinnerNgayDen = new JSpinner(modelDen);
        JSpinner.DateEditor editorDen = new JSpinner.DateEditor(spinnerNgayDen, "yyyy-MM-dd HH:mm:ss");
        spinnerNgayDen.setEditor(editorDen);
        spinnerNgayDen.setValue(new Date());
        panelForm.add(createInputField("Ngày đến:", spinnerNgayDen));

        boxTrangThai = new JComboBox<>(TrangThaiLichTrinh.values());
        panelForm.add(createInputField("Trạng thái:", boxTrangThai));

        // Khởi tạo các nút
        buttonThem = new JButton("Thêm");
        buttonThem.setEnabled(true);
        buttonSua = new JButton("Sửa");
        buttonSua.setEnabled(false);
        buttonXoa = new JButton("Xoá");
        buttonXoa.setEnabled(false);
        buttonReset = new JButton("Reset");
        buttonReset.setEnabled(true);

        // Mảng nút đã xóa buttonLuu
        JButton[] buttons = {buttonThem, buttonSua, buttonXoa, buttonReset};

        panelTop.add(panelHome, BorderLayout.NORTH);
        panelTop.add(panelForm);
        panelTop.add(createButtonField(buttons), BorderLayout.SOUTH);

        Object[] columns = new Object[]{"Mã LT", "Tàu", "Tuyến đường", "Ngày đi", "Ngày đến", "Trạng thái"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // --- GETTERS & SETTERS ---

    public String getMaLichTrinh() {
        return fieldMaLichTrinh.getText().trim();
    }

    public void setMaLichTrinh(String maLichTrinh) {
        fieldMaLichTrinh.setText(maLichTrinh);
    }

    public JComboBox<Tau> getBoxTau() {
        return boxTau;
    }

    public Tau getTau() {
        return (Tau) boxTau.getSelectedItem();
    }

    public JComboBox<TuyenDuong> getBoxTuyenDuong() {
        return boxTuyenDuong;
    }

    public TuyenDuong getTuyenDuong() {
        return (TuyenDuong) boxTuyenDuong.getSelectedItem();
    }

    public LocalDateTime getNgayDi() {
        Date date = (Date) spinnerNgayDi.getValue();
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void setNgayDi(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            spinnerNgayDi.setValue(new Date());
        } else {
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            spinnerNgayDi.setValue(date);
        }
    }

    public LocalDateTime getNgayDen() {
        Date date = (Date) spinnerNgayDen.getValue();
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void setNgayDen(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            spinnerNgayDen.setValue(new Date());
        } else {
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            spinnerNgayDen.setValue(date);
        }
    }

    public String getTrangThai() {
        TrangThaiLichTrinh trangThai = (TrangThaiLichTrinh) boxTrangThai.getSelectedItem();
        return trangThai != null ? trangThai.name() : TrangThaiLichTrinh.CHO.name();
    }

    public void setTrangThai(String trangThaiStr) {
        try {
            boxTrangThai.setSelectedItem(TrangThaiLichTrinh.valueOf(trangThaiStr));
        } catch (IllegalArgumentException | NullPointerException e) {
            boxTrangThai.setSelectedIndex(0);
        }
    }

    public LichTrinh getLichTrinhFromForm() {
        String maLT = getMaLichTrinh();
        Tau tau = getTau();
        TuyenDuong tuyen = getTuyenDuong();
        LocalDateTime ngayDi = getNgayDi();
        LocalDateTime ngayDen = getNgayDen();
        String trangThai = getTrangThai();

        if (maLT.isEmpty()) throw new IllegalArgumentException("Mã lịch trình không được để trống!");
        if (tau == null) throw new IllegalArgumentException("Vui lòng chọn Tàu!");
        if (tuyen == null) throw new IllegalArgumentException("Vui lòng chọn Tuyến đường!");

        // Validate logic thời gian
        LocalDateTime hienTai = LocalDateTime.now();
        if (ngayDi.isBefore(hienTai)) {
            // Uncomment dòng dưới nếu muốn chặn ngày quá khứ
            // throw new IllegalArgumentException("Ngày đi không thể là ngày trong quá khứ!");
        }

        if (ngayDen.isBefore(ngayDi)) {
            throw new IllegalArgumentException("Ngày đến không thể trước ngày đi!");
        }

        return new LichTrinh(maLT, tau.getId(), tuyen.getId(), ngayDi, ngayDen, trangThai);
    }

    public JTable getTable() {
        return table;
    }

    // --- LOGIC TRẠNG THÁI BUTTON ---

    public void startEditMode() {
        isEditMode = true;

        fieldMaLichTrinh.setEnabled(false);
        fieldMaLichTrinh.setBackground(new Color(240, 240, 240));

        buttonThem.setEnabled(false);
        buttonSua.setEnabled(true);
        // Bỏ buttonLuu
        buttonXoa.setEnabled(true);
        buttonReset.setEnabled(true);
    }

    public void resetForm() {
        isEditMode = false;

        fieldMaLichTrinh.setEnabled(true);
        fieldMaLichTrinh.setText("");
        fieldMaLichTrinh.setBackground(Color.WHITE);

        if (boxTau.getItemCount() > 0) boxTau.setSelectedIndex(0);
        if (boxTuyenDuong.getItemCount() > 0) boxTuyenDuong.setSelectedIndex(0);

        spinnerNgayDi.setValue(new Date());
        spinnerNgayDen.setValue(new Date());

        if (boxTrangThai.getItemCount() > 0) boxTrangThai.setSelectedIndex(0);

        buttonThem.setEnabled(true);
        buttonSua.setEnabled(false);
        // Bỏ buttonLuu
        buttonXoa.setEnabled(false);
        buttonReset.setEnabled(true);

        if (table != null) {
            table.clearSelection();
        }
    }

    // --- LISTENERS (Đã bỏ addLuuListener) ---

    public void addThemListener(ActionListener l) { buttonThem.addActionListener(l); }
    public void addSuaListener(ActionListener l) { buttonSua.addActionListener(l); }
    public void addXoaListener(ActionListener l) { buttonXoa.addActionListener(l); }
    public void addResetListener(ActionListener l) { buttonReset.addActionListener(l); }
}