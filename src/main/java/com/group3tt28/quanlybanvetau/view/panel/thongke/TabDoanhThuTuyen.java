package com.group3tt28.quanlybanvetau.view.panel.thongke;

import com.group3tt28.quanlybanvetau.model.dto.DoanhThuTuyen;
import com.group3tt28.quanlybanvetau.view.panel.BasePanel;
import com.toedter.calendar.JDateChooser;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TabDoanhThuTuyen extends BasePanel {

    private JDateChooser chooserTuNgay;
    private JDateChooser chooserDenNgay;

    private JButton buttonThongKe;

    private ButtonGroup viewModeGroup;
    private JToggleButton buttonXemBieuDo;
    private JToggleButton buttonXemBang;

    private JPanel mainContainer;

    private JTable table;
    private DefaultTableModel tableModel;

    private JFreeChart currentChart;
    private List<DoanhThuTuyen> currentList;

    public TabDoanhThuTuyen() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout(0, 0));

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTop.setBackground(Color.WHITE);

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

        panelTop.add(createInputField("Từ ngày", chooserTuNgay, Color.WHITE));
        panelTop.add(createInputField("Đến ngày", chooserDenNgay, Color.WHITE));
        panelTop.add(buttonThongKe);
        panelTop.add(Box.createHorizontalStrut(30));
        panelTop.add(buttonXemBieuDo);
        panelTop.add(buttonXemBang);

        mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(Color.WHITE);
        mainContainer.setBorder(new EmptyBorder(10, 10, 10, 10));

        mainContainer.add(new JLabel("Chưa có dữ liệu.", JLabel.CENTER), BorderLayout.CENTER);

        String[] columns = {"STT", "Tên tuyến", "Doanh thu"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getTableHeader().setFont(FONT_PLAIN);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);

        add(panelTop, BorderLayout.NORTH);
        add(mainContainer, BorderLayout.CENTER);

        buttonXemBieuDo.addActionListener(e -> veBieuDo());
        buttonXemBang.addActionListener(e -> veBang());
    }

    public void setData(JFreeChart chart, List<DoanhThuTuyen> list) {
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

            CategoryPlot plot = currentChart.getCategoryPlot();
            plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_90);

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
            double doanhThu = 0;
            NumberFormat tienVN = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("vi-VN"));
            for (DoanhThuTuyen item : currentList) {
                tableModel.addRow(new Object[]{
                        stt++,
                        item.getTenTuyen(),
                        tienVN.format(item.getDoanhThu()),
                });
                doanhThu += item.getDoanhThu();
            }
            tableModel.fireTableDataChanged();

            JLabel labelTitle = new JLabel("DOANH THU THEO TUYẾN", JLabel.CENTER);
            labelTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
            labelTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
            mainContainer.add(labelTitle, BorderLayout.NORTH);

            mainContainer.add(new JScrollPane(table), BorderLayout.CENTER);

            JLabel labelDoanhThu = new JLabel("TỔNG DOANH THU: " + tienVN.format(doanhThu), JLabel.RIGHT);
            labelDoanhThu.setFont(new Font("Segoe UI", Font.BOLD, 16));
            labelDoanhThu.setForeground(Color.RED);
            mainContainer.add(labelDoanhThu, BorderLayout.SOUTH);
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

    public void addThongKeListener(ActionListener listener) {
        buttonThongKe.addActionListener(listener);
    }
}
