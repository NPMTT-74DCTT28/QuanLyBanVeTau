package com.group3tt28.quanlybanvetau.view.panel.thongke;

import com.group3tt28.quanlybanvetau.model.dto.DoanhThuNgay;
import com.group3tt28.quanlybanvetau.view.panel.BasePanel;
import com.toedter.calendar.JDateChooser;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TabDoanhThuNgay extends BasePanel {

    private JDateChooser chooserTuNgay;
    private JDateChooser chooserDenNgay;

    private JButton buttonThongKe;

    private JToggleButton buttonXemBang, buttonXemBieuDo;
    private ButtonGroup viewModeGroup;

    private JPanel mainContainer;

    private JFreeChart currentChart;
    private List<DoanhThuNgay> currentList;

    private JTable table;
    private DefaultTableModel tableModel;

    public TabDoanhThuNgay() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout(0, 0));

        JPanel panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelNorth.setBackground(Color.WHITE);

        chooserTuNgay = new JDateChooser();
        chooserTuNgay.setDateFormatString("dd/MM/yyyy");
        chooserTuNgay.setDate(new Date());

        chooserDenNgay = new JDateChooser();
        chooserDenNgay.setDateFormatString("dd/MM/yyyy");
        chooserDenNgay.setDate(new Date());

        buttonThongKe = createStyledButton("Xem kết quả", new Dimension(120, 40), SECONDARY_COLOR, Color.BLACK);

        buttonXemBieuDo = new JToggleButton("Biểu đồ");
        buttonXemBieuDo.setSelected(true);

        buttonXemBang = new JToggleButton("Bảng số liệu");

        viewModeGroup = new ButtonGroup();
        viewModeGroup.add(buttonXemBieuDo);
        viewModeGroup.add(buttonXemBang);

        panelNorth.add(createInputField("Từ ngày", chooserTuNgay, Color.WHITE));
        panelNorth.add(createInputField("Đến ngày", chooserDenNgay, Color.WHITE));
        panelNorth.add(buttonThongKe);
        panelNorth.add(Box.createHorizontalStrut(30));
        panelNorth.add(buttonXemBieuDo);
        panelNorth.add(buttonXemBang);

        mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(Color.WHITE);
        mainContainer.setBorder(new EmptyBorder(10, 10, 10, 10));

        mainContainer.add(new JLabel("Chưa có dữ liệu.", JLabel.CENTER), BorderLayout.CENTER);

        String[] columns = {"STT", "Ngày", "Số vé bán", "Doanh thu"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getTableHeader().setFont(FONT_PLAIN);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

        add(panelNorth, BorderLayout.NORTH);
        add(mainContainer, BorderLayout.CENTER);

        buttonXemBieuDo.addActionListener(e -> veBieuDo());
        buttonXemBang.addActionListener(e -> veBang());
    }

    public void setData(JFreeChart chart, List<DoanhThuNgay> list) {
        this.currentChart = chart;
        this.currentList = list;

        if (buttonXemBieuDo.isSelected()) {
            veBieuDo();
        } else {
            veBang();
        }
    }

    private void veBieuDo() {
        mainContainer.removeAll();

        if (currentChart != null) {
            ChartPanel panel = new ChartPanel(currentChart);
            panel.setBackground(Color.WHITE);
            panel.setMouseWheelEnabled(true);
            panel.setMinimumDrawWidth(0);
            panel.setMinimumDrawHeight(0);
            mainContainer.add(panel, BorderLayout.CENTER);
        } else {
            mainContainer.add(new JLabel("Chưa có dữ liệu.", JLabel.CENTER), BorderLayout.CENTER);
        }

        mainContainer.revalidate();
        mainContainer.repaint();
    }

    private void veBang() {
        mainContainer.removeAll();

        if (currentList != null && !currentList.isEmpty()) {
            tableModel.setRowCount(0);
            int stt = 1;
            double tongDoanhThu = 0;
            NumberFormat tienVN = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("vi-VN"));
            for (DoanhThuNgay item : currentList) {
                tableModel.addRow(new Object[]{
                        stt++,
                        formatNgayVN(item.getNgay()),
                        item.getSoVeBan(),
                        tienVN.format(item.getDoanhThu())
                });
                tongDoanhThu += item.getDoanhThu();
            }
            tableModel.fireTableDataChanged();

            JLabel title = new JLabel("BẢNG THỐNG KÊ DOANH THU", JLabel.CENTER);
            title.setFont(new Font("Segoe UI", Font.BOLD, 20));
            title.setBorder(new EmptyBorder(0, 0, 10, 0));
            mainContainer.add(title, BorderLayout.NORTH);

            mainContainer.add(new JScrollPane(table), BorderLayout.CENTER);

            JLabel labelTong = new JLabel("TỔNG DOANH THU: " + tienVN.format(tongDoanhThu), JLabel.RIGHT);
            labelTong.setFont(new Font("Segoe UI", Font.BOLD, 16));
            labelTong.setForeground(Color.RED);
            mainContainer.add(labelTong, BorderLayout.SOUTH);
        } else {
            mainContainer.add(new JLabel("Chưa có dữ liệu.", JLabel.CENTER), BorderLayout.CENTER);
        }

        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public LocalDate getTuNgay() {
        if (chooserTuNgay.getDate() != null) {
            return chooserTuNgay.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }

    public LocalDate getDenNgay() {
        if (chooserDenNgay.getDate() != null) {
            return chooserDenNgay.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }

    private String formatNgayVN(String sqlDate) {
        try {
            LocalDate date = LocalDate.parse(sqlDate);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return date.format(formatter);
        } catch (Exception e) {
            return sqlDate;
        }
    }

    public void addThongKeListener(ActionListener l) {
        buttonThongKe.addActionListener(l);
    }
}
