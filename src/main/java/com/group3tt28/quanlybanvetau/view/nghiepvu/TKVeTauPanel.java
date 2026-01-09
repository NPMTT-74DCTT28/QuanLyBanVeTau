package com.group3tt28.quanlybanvetau.view.nghiepvu;

import com.group3tt28.quanlybanvetau.view.BasePanel;

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

public class TKVeTauPanel extends BasePanel {
    private JTextField fieldTuKhoa;
    private DefaultTableModel model;
    private JButton buttonTimKiem, buttonReset, buttonLammoi;
    private JTable table;

    public TKVeTauPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.WHITE);

        JPanel panelTop = new JPanel(new BorderLayout(5, 5));
        panelTop.setBackground(Color.WHITE);

        JPanel panelTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelTitle.setBackground(PRIMARY_COLOR);
        JLabel labelTitle = new JLabel("Danh sách Vé tàu");
        labelTitle.setFont(new Font("segoe UI", Font.BOLD, 20));
        labelTitle.setForeground(Color.WHITE);
        panelTitle.add(labelTitle);

        JPanel panelForm = new JPanel(new GridLayout(1, 3, 5, 5));
        panelForm.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelForm.setBackground(Color.WHITE);

        fieldTuKhoa = new JTextField();
        panelForm.add(createInputField("<html>Mã vé/<br>Lịch trình</html>", fieldTuKhoa, Color.WHITE));

        buttonTimKiem = createStyledButton("Tìm kiếm", new Dimension(100, 40), PRIMARY_COLOR, Color.WHITE);
        buttonReset = createStyledButton("Reset form", new Dimension(110, 40), PRIMARY_COLOR, Color.WHITE);
        buttonLammoi = createStyledButton("Làm mới", new Dimension(100, 40), PRIMARY_COLOR, Color.WHITE);
        JButton[] buttons = new JButton[]{buttonTimKiem, buttonReset, buttonLammoi};

        panelTop.add(panelTitle, BorderLayout.NORTH);
        panelTop.add(panelForm, BorderLayout.CENTER);
        panelTop.add(createButtonField(buttons, Color.WHITE), BorderLayout.SOUTH);

        Object[] columns = new Object[]{"ID", "Mã vé", "ID Khách hàng", "ID Lịch trình", "ID Ghế", "ID Nhân viên", "Ngày đặt", "Giá vé", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        table = new JTable(model);
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn columnId = columnModel.getColumn(0);
        table.removeColumn(columnId);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(SECONDARY_COLOR);
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setOpaque(false);
        table.setFont(FONT_PLAIN);

        JScrollPane scrollPane = new JScrollPane(table);

        TitledBorder tableBorder = new TitledBorder(new LineBorder(Color.LIGHT_GRAY), "Danh sách Vé tàu",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, FONT_BOLD, Color.BLACK);

        scrollPane.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), tableBorder));
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

    public String getTuKhoa() {
        return fieldTuKhoa.getText().trim();
    }

    public JTable getTable() {
        return table;
    }

    public void resetForm() {
        fieldTuKhoa.setText("");
    }

    public void TimKiemListener(ActionListener l) {
        buttonTimKiem.addActionListener(l);
    }

    public void ResetFormListener(ActionListener l) {
        buttonReset.addActionListener(l);
    }

    public void LamMoiListener(ActionListener l) {
        buttonLammoi.addActionListener(l);
    }
}
