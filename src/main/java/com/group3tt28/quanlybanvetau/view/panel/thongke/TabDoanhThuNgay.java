package com.group3tt28.quanlybanvetau.view.panel.thongke;

import com.group3tt28.quanlybanvetau.model.dto.DoanhThuNgay;
import com.group3tt28.quanlybanvetau.util.DinhDang;
import com.toedter.calendar.JDateChooser;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class TabDoanhThuNgay extends BaseThongKeTab<DoanhThuNgay> {

    private JDateChooser chooserTuNgay;
    private JDateChooser chooserDenNgay;
    private JButton buttonThongKe;

    private JToggleButton buttonXemBang, buttonXemBieuDo;
    private ButtonGroup viewModeGroup;

    private JFreeChart currentChart;
    private List<DoanhThuNgay> currentList;

    public TabDoanhThuNgay() {
        super();
    }

    @Override
    protected void initComponents() {
        super.initComponents();

        JPanel panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelNorth.setBackground(Color.WHITE);
        panelNorth.setBorder(BorderFactory.createMatteBorder(0, 0, 10, 0, Color.LIGHT_GRAY));

        chooserTuNgay = new JDateChooser();
        chooserTuNgay.setDateFormatString("dd/MM/yyyy");
        chooserTuNgay.setDate(new Date());

        chooserDenNgay = new JDateChooser();
        chooserDenNgay.setDateFormatString("dd/MM/yyyy");
        chooserDenNgay.setDate(new Date());

        buttonThongKe = createStyledButton("Xem kết quả", new Dimension(120, 38), SECONDARY_COLOR, Color.BLACK);

        buttonXemBieuDo = new JToggleButton("Biểu đồ");
        buttonXemBieuDo.setPreferredSize(new Dimension(80, 40));
        buttonXemBieuDo.setBackground(Color.WHITE);
        buttonXemBieuDo.setSelected(true);

        buttonXemBang = new JToggleButton("Bảng số liệu");
        buttonXemBang.setPreferredSize(new Dimension(110, 40));
        buttonXemBang.setBackground(Color.WHITE);

        viewModeGroup = new ButtonGroup();
        viewModeGroup.add(buttonXemBieuDo);
        viewModeGroup.add(buttonXemBang);

        panelNorth.add(createInputField("Từ ngày", chooserTuNgay, Color.WHITE));
        panelNorth.add(createInputField("Đến ngày", chooserDenNgay, Color.WHITE));
        panelNorth.add(buttonThongKe);
        panelNorth.add(Box.createHorizontalStrut(20));
        panelNorth.add(buttonXemBieuDo);
        panelNorth.add(buttonXemBang);

        add(panelNorth, BorderLayout.NORTH);

        if (table.getColumnCount() >= 4) {
            table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        }

        buttonXemBieuDo.addActionListener(e -> super.veBieuDo(currentChart));
        buttonXemBang.addActionListener(e -> super.veBang(currentList));
    }

    @Override
    protected String[] getTenCot() {
        return new String[]{"STT", "Ngày", "Số vé bán", "Doanh thu"};
    }

    @Override
    protected String getTieuDeBang() {
        return "DOANH THU THEO NGÀY";
    }

    @Override
    protected Object[] getRowData(int stt, DoanhThuNgay item) {
        return new Object[]{
                stt,
                DinhDang.formatNgayVN(item.getNgay()),
                item.getSoVeBan(),
                tienVN.format(item.getDoanhThu()),
        };
    }

    @Override
    protected String getTextTongKet(List<DoanhThuNgay> listData) {
        double tongDoanhThu = 0;
        for (DoanhThuNgay item : listData) {
            tongDoanhThu += item.getDoanhThu();
        }
        return "TỔNG DOANH THU: " + tienVN.format(tongDoanhThu);
    }

    public void setData(JFreeChart chart, List<DoanhThuNgay> list) {
        this.currentChart = chart;
        this.currentList = list;

        if (buttonXemBieuDo.isSelected()) {
            super.veBieuDo(currentChart);
        } else {
            super.veBang(currentList);
        }
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

    public void addThongKeListener(ActionListener l) {
        buttonThongKe.addActionListener(l);
    }
}