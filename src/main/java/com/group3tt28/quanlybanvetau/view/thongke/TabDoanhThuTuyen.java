package com.group3tt28.quanlybanvetau.view.thongke;

import com.group3tt28.quanlybanvetau.model.dto.DoanhThuTuyen;
import com.group3tt28.quanlybanvetau.util.DinhDang;
import com.group3tt28.quanlybanvetau.view.BaseThongKeTab;
import com.toedter.calendar.JDateChooser;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class TabDoanhThuTuyen extends BaseThongKeTab<DoanhThuTuyen> {

    private JDateChooser chooserTuNgay;
    private JDateChooser chooserDenNgay;

    private JButton buttonThongKe;

    private ButtonGroup viewModeGroup;
    private JToggleButton buttonXemBieuDo;
    private JToggleButton buttonXemBang;

    private JFreeChart chart;
    private List<DoanhThuTuyen> listData;

    public TabDoanhThuTuyen() {
        super();
    }

    @Override
    protected void initComponents() {
        super.initComponents();

        JPanel panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelNorth.setBackground(Color.white);
        panelNorth.setBorder(new MatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));

        chooserTuNgay = new JDateChooser();
        chooserTuNgay.setDateFormatString(DinhDang.DATE_VN);
        chooserTuNgay.setDate(new Date());
        chooserTuNgay.setMaxSelectableDate(new Date(System.currentTimeMillis()));

        chooserDenNgay = new JDateChooser();
        chooserDenNgay.setDateFormatString(DinhDang.DATE_VN);
        chooserDenNgay.setDate(new Date());
        chooserDenNgay.setMaxSelectableDate(new Date(System.currentTimeMillis()));

        buttonThongKe = createStyledButton("Xem kết quả", new Dimension(120, 40), SECONDARY_COLOR, Color.BLACK);

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
        panelNorth.add(Box.createHorizontalStrut(30));
        panelNorth.add(buttonXemBieuDo);
        panelNorth.add(buttonXemBang);

        add(panelNorth, BorderLayout.NORTH);

        if (table.getColumnCount() >= 3) {
            table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
            table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        }

        buttonXemBieuDo.addActionListener(e -> super.veBieuDo(chart));
        buttonXemBang.addActionListener(e -> super.veBang(listData));
    }

    @Override
    protected String[] getTenCot() {
        return new String[]{"Hạng", "Tên tuyến", "Doanh thu"};
    }

    @Override
    protected String getTieuDeBang() {
        return "BẢNG XẾP HẠNG DOANH THU THEO TUYẾN TỪ " + DinhDang.formatNgayVN(getTuNgay()) + " ĐẾN " + DinhDang.formatNgayVN(getDenNgay());
    }

    @Override
    protected Object[] getRowData(int stt, DoanhThuTuyen item) {
        return new Object[]{
                stt,
                item.getTenTuyen(),
                DinhDang.formatTienVN(item.getDoanhThu())
        };
    }

    @Override
    protected String getTextTongKet(List<DoanhThuTuyen> listData) {
        double doanhThu = 0;
        for (DoanhThuTuyen item : listData) {
            doanhThu += item.getDoanhThu();
        }
        return "TỔNG DOANH THU: " + DinhDang.formatTienVN(doanhThu);
    }

    public void setData(JFreeChart chart, List<DoanhThuTuyen> listData) {
        this.chart = chart;
        this.listData = listData;

        if (buttonXemBieuDo.isSelected()) {
            super.veBieuDo(chart);
        } else if (buttonXemBang.isSelected()) {
            super.veBang(listData);
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

    public void addThongKeListener(ActionListener listener) {
        buttonThongKe.addActionListener(listener);
    }
}
