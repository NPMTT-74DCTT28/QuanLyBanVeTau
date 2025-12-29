package com.group3tt28.quanlybanvetau.view.panel;

import com.group3tt28.quanlybanvetau.model.LoaiToa;
import com.group3tt28.quanlybanvetau.model.Tau;
import com.group3tt28.quanlybanvetau.model.ToaTau;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public final class QLToaTauPanel extends BasePanel {

    private JTextField fieldMaToa;
    private JComboBox<Tau> boxTau;
    private JComboBox<LoaiToa> boxLoaiToa;

    // --- KHAI BÁO THÊM UI TÌM KIẾM ---
    private JTextField fieldTimKiem;
    private JButton buttonTimKiem;

    private JButton buttonThem, buttonSua, buttonXoa, buttonReset;
    private JTable table;
    private boolean isEditMode = false;

    public QLToaTauPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelHome = new JPanel();
        panelHome.setBackground(new Color(152, 251, 152));
        panelHome.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel labelHome = new JLabel("Quản lý toa tàu");
        labelHome.setSize(200, 80);
        labelHome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panelHome.add(labelHome);

        JPanel panelTop = new JPanel(new BorderLayout(0, 5));
        JPanel panelForm = new JPanel(new GridLayout(3, 1, 5, 5));
        panelForm.setBorder(new EmptyBorder(10, 5, 10, 5));

        fieldMaToa = new JTextField();
        panelForm.add(createInputField("Mã toa:", fieldMaToa, Color.WHITE));

        boxTau = new JComboBox<>();
        panelForm.add(createInputField("Thuộc tàu:", boxTau, Color.WHITE));

        boxLoaiToa = new JComboBox<>();
        panelForm.add(createInputField("Loại toa:", boxLoaiToa, Color.WHITE));

        // --- KHU VỰC CHỨC NĂNG (BUTTONS + TÌM KIẾM) ---
        JPanel panelActions = new JPanel(new BorderLayout(5, 5));
        panelActions.setBorder(new EmptyBorder(0, 5, 5, 5));

        // 1. Panel Tìm kiếm (Mới)
        JPanel panelTimKiemContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelTimKiemContainer.setBackground(Color.WHITE);

        JLabel labelTim = new JLabel("Tìm kiếm (Mã toa/Tên tàu): ");
        labelTim.setFont(new Font("Segoe UI", Font.BOLD, 12));

        fieldTimKiem = new JTextField(20);
        fieldTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        buttonTimKiem = createStyledButton("Tìm", new Dimension(80, 30), new Color(70, 130, 180), Color.WHITE); // Màu xanh dương

        panelTimKiemContainer.add(labelTim);
        panelTimKiemContainer.add(fieldTimKiem);
        panelTimKiemContainer.add(buttonTimKiem);

        // 2. Panel Button CRUD
        buttonThem = createStyledButton("Thêm", new Dimension(80,40),PRIMARY_COLOR, Color.WHITE);
        buttonThem.setEnabled(true);
        buttonSua = createStyledButton("Sửa", new Dimension(80,40), new Color(200,200,40), Color.WHITE);
        buttonSua.setEnabled(false);
        buttonXoa = createStyledButton("Xoá", new Dimension(80, 40), Color.RED, Color.white);
        buttonXoa.setEnabled(false);
        buttonReset = createStyledButton("Reset form", new Dimension(110, 40), PRIMARY_COLOR, Color.WHITE);
        buttonReset.setEnabled(true);

        JButton[] buttons = {buttonThem, buttonSua, buttonXoa, buttonReset};

        // Dùng JComponent để hứng kết quả trả về từ createButtonField (tránh lỗi ép kiểu)
        JComponent panelButtons = createButtonField(buttons, Color.WHITE);

        // Ghép vào panelActions
        panelActions.add(panelTimKiemContainer, BorderLayout.NORTH);
        panelActions.add(panelButtons, BorderLayout.CENTER);

        // Ghép tất cả vào panelTop
        panelTop.add(panelHome, BorderLayout.NORTH);
        panelTop.add(panelForm, BorderLayout.CENTER);
        panelTop.add(panelActions, BorderLayout.SOUTH);

        // Table
        Object[] columns = new Object[]{"ID", "Mã Toa", "Tàu", "Loại Toa"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);

        // Ẩn cột ID đi nếu muốn (nhưng ở đây cứ để hiển thị để dễ debug)
        table.getColumnModel().getColumn(0).setPreferredWidth(50);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public String getMaToa() {
        return fieldMaToa.getText().trim();
    }

    public void setMaToa(String maToa) {
        fieldMaToa.setText(maToa);
    }

    public JComboBox<Tau> getBoxTau() {
        return boxTau;
    }

    public Tau getSelectedTau() {
        return (Tau) boxTau.getSelectedItem();
    }

    public JComboBox<LoaiToa> getBoxLoaiToa() {
        return boxLoaiToa;
    }

    public LoaiToa getSelectedLoaiToa() {
        return (LoaiToa) boxLoaiToa.getSelectedItem();
    }

    // --- GETTER UI TÌM KIẾM ---
    public String getTuKhoaTimKiem() {
        return fieldTimKiem.getText().trim();
    }

    public ToaTau getToaTauFromForm() {
        String maToa = getMaToa();
        Tau tau = getSelectedTau();
        LoaiToa loaiToa = getSelectedLoaiToa();

        if (maToa.isEmpty()) throw new IllegalArgumentException("Mã toa không được để trống!");
        if (tau == null) throw new IllegalArgumentException("Vui lòng chọn Tàu!");
        if (loaiToa == null) throw new IllegalArgumentException("Vui lòng chọn Loại toa!");

        return new ToaTau(maToa, tau.getId(), loaiToa.getId());
    }

    public JTable getTable() {
        return table;
    }

    public void startEditMode() {
        isEditMode = true;
        fieldMaToa.setEnabled(false);
        fieldMaToa.setBackground(new Color(240, 240, 240));

        buttonThem.setEnabled(false);
        buttonSua.setEnabled(true);
        buttonXoa.setEnabled(true);
        buttonReset.setEnabled(true);
    }

    public void resetForm() {
        isEditMode = false;
        fieldMaToa.setEnabled(true);
        fieldMaToa.setText("");
        fieldMaToa.setBackground(Color.WHITE);

        if (boxTau.getItemCount() > 0) boxTau.setSelectedIndex(0);
        if (boxLoaiToa.getItemCount() > 0) boxLoaiToa.setSelectedIndex(0);

        // Reset cả ô tìm kiếm
        fieldTimKiem.setText("");

        buttonThem.setEnabled(true);
        buttonSua.setEnabled(false);
        buttonXoa.setEnabled(false);
        buttonReset.setEnabled(true);

        if (table != null) table.clearSelection();
    }

    public void addThemListener(ActionListener l) { buttonThem.addActionListener(l); }
    public void addSuaListener(ActionListener l) { buttonSua.addActionListener(l); }
    public void addXoaListener(ActionListener l) { buttonXoa.addActionListener(l); }
    public void addResetListener(ActionListener l) { buttonReset.addActionListener(l); }

    // Listener tìm kiếm
    public void addTimKiemListener(ActionListener l) { buttonTimKiem.addActionListener(l); }
}