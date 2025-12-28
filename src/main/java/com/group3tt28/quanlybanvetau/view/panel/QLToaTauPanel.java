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

    // Đã bỏ buttonLuu
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

        buttonThem = createStyledButton("Thêm", new Dimension(80,40),PRIMARY_COLOR, Color.WHITE);
        buttonThem.setEnabled(true);
        buttonSua = createStyledButton("Sửa", new Dimension(80,40), new Color(200,200,40), Color.WHITE);
        buttonSua.setEnabled(false);
        buttonXoa = createStyledButton("Xoá", new Dimension(80, 40), Color.RED, Color.white);
        buttonXoa.setEnabled(false);
        buttonReset = createStyledButton("Reset form", new Dimension(110, 40), PRIMARY_COLOR, Color.WHITE);
        buttonReset.setEnabled(true);

        // Mảng nút đã xóa buttonLuu
        JButton[] buttons = {buttonThem, buttonSua, buttonXoa, buttonReset};

        panelTop.add(panelHome, BorderLayout.NORTH);
        panelTop.add(panelForm, BorderLayout.CENTER);
        panelTop.add(createButtonField(buttons, Color.WHITE), BorderLayout.SOUTH);

        Object[] columns = new Object[]{"ID", "Mã Toa", "Tàu", "Loại Toa"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);

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
        // Bỏ buttonLuu
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

        buttonThem.setEnabled(true);
        buttonSua.setEnabled(false);
        // Bỏ buttonLuu
        buttonXoa.setEnabled(false);
        buttonReset.setEnabled(true);

        if (table != null) table.clearSelection();
    }

    public void addThemListener(ActionListener l) { buttonThem.addActionListener(l); }
    public void addSuaListener(ActionListener l) { buttonSua.addActionListener(l); }
    public void addXoaListener(ActionListener l) { buttonXoa.addActionListener(l); }
    public void addResetListener(ActionListener l) { buttonReset.addActionListener(l); }
}