package com.group3tt28.quanlybanvetau.view.panel.thongke;

import com.group3tt28.quanlybanvetau.model.dto.DoanhSoNhanVien;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class TabThongKeDoanhSo extends BaseThongKeTab<DoanhSoNhanVien> {

    private JSpinner spinnerThang, spinnerNam;

    private JButton buttonThongKe;

    private ButtonGroup viewModeGroup;
    private JToggleButton buttonXemBieuDo, buttonXemBang;

    private JFreeChart currentChart;
    private List<DoanhSoNhanVien> currenntList;

    public TabThongKeDoanhSo() {
        super();
    }

    @Override
    protected void initComponents() {
        super.initComponents();

        JPanel panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelNorth.setBackground(Color.WHITE);
        panelNorth.setBorder(new MatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));

        int currentYear = LocalDate.now().getYear();
        int startYear = 2020;

        spinnerThang = new JSpinner();
        spinnerThang.setModel(new SpinnerNumberModel(1, 1, 12, 1));

        spinnerNam = new JSpinner();
        spinnerNam.setModel(new SpinnerNumberModel(currentYear, startYear, currentYear, 1));
        spinnerNam.setEditor(new JSpinner.NumberEditor(spinnerNam, "#"));

        buttonThongKe = createStyledButton("Xem kết quả", new Dimension(120, 40), SECONDARY_COLOR, Color.BLACK);

        buttonXemBieuDo = new JToggleButton("Biểu đồ");
        buttonXemBieuDo.setBackground(Color.WHITE);
        buttonXemBieuDo.setPreferredSize(new Dimension(80, 40));
        buttonXemBieuDo.setSelected(true);

        buttonXemBang = new JToggleButton("Bảng số liệu");
        buttonXemBang.setBackground(Color.WHITE);
        buttonXemBang.setPreferredSize(new Dimension(110, 40));

        viewModeGroup = new ButtonGroup();
        viewModeGroup.add(buttonXemBieuDo);
        viewModeGroup.add(buttonXemBang);

        panelNorth.add(createInputField("Tháng", spinnerThang, Color.WHITE));
        panelNorth.add(createInputField("Năm", spinnerNam, Color.WHITE));
        panelNorth.add(buttonThongKe);
        panelNorth.add(Box.createHorizontalStrut(30));
        panelNorth.add(buttonXemBieuDo);
        panelNorth.add(buttonXemBang);

        add(panelNorth, BorderLayout.NORTH);

        if (table.getColumnCount() >= 5) {
            table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
            table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
            table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        }

        buttonXemBieuDo.addActionListener(e -> super.veBieuDo(currentChart));
        buttonXemBang.addActionListener(e -> super.veBang(currenntList));
    }

    @Override
    protected String[] getTenCot() {
        return new String[]{"STT", "Mã nhân viên", "Họ tên", "Số vé bán", "Doanh số (VNĐ)"};
    }

    @Override
    protected String getTieuDeBang() {
        return "DOANH SỐ NHÂN VIÊN";
    }

    @Override
    protected Object[] getRowData(int stt, DoanhSoNhanVien item) {
        return new Object[]{
                stt,
                item.getMaNhanVien(),
                item.getHoTen(),
                item.getSoVeBan(),
                tienVN.format(item.getDoanhSo())
        };
    }

    @Override
    protected String getTextTongKet(List<DoanhSoNhanVien> listData) {
        return "";
    }

    public void setData(JFreeChart chart, List<DoanhSoNhanVien> listData) {
        this.currentChart = chart;
        this.currenntList = listData;

        if (buttonXemBieuDo.isSelected()) {
            super.veBieuDo(currentChart);
        } else if (buttonXemBang.isSelected()) {
            super.veBang(currenntList);
        }
    }

    public int getThang() {
        Object value = spinnerThang.getValue();
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return 1;
    }

    public int getNam() {
        Object value = spinnerNam.getValue();
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return LocalDate.now().getYear();
    }

    public void addThongKeListener(ActionListener listener) {
        buttonThongKe.addActionListener(listener);
    }
}
