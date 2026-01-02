package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.ThongKeDAO;
import com.group3tt28.quanlybanvetau.model.dto.DoanhSoNhanVien;
import com.group3tt28.quanlybanvetau.util.DinhDang;
import com.group3tt28.quanlybanvetau.view.panel.thongke.TabThongKeDoanhSo;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

public class ThongKeDoanhSoController {

    private final TabThongKeDoanhSo tab;
    private final ThongKeDAO dao;

    public ThongKeDoanhSoController(TabThongKeDoanhSo tab) {
        this.tab = tab;
        this.dao = ThongKeDAO.getInstance();

        this.tab.addThongKeListener(new ThongKeListener());

        this.tab.setVisible(true);
    }

    private class ThongKeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int thang = tab.getThang();
                int nam = tab.getNam();

                int currentYear = LocalDate.now().getYear();
                int currentMonth = LocalDate.now().getMonthValue();

                if (thang < 1 || thang > 12) {
                    tab.showWarning("Tháng phải từ 1-12.");
                    return;
                }

                if (nam > currentYear) {
                    tab.showWarning("Không thể thống kê dữ liệu trong tương lai.");
                    return;
                }

                if (nam == currentYear && thang > currentMonth) {
                    tab.showMessage("Chưa có dữ liệu của tháng " + thang + "!");
                    return;
                }

                List<DoanhSoNhanVien> listData = dao.getDoanhSoNhanVien(thang, nam);
                listData.removeIf(doanhSoNhanVien -> doanhSoNhanVien.getDoanhSo() == 0);
                if (listData.isEmpty()) {
                    tab.showMessage("Không có dữ liệu thống kê trong tháng " + thang + "/" + nam + "!");
                    tab.setData(null, null);
                    return;
                }

                DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
                for (DoanhSoNhanVien item : listData) {
                    dataset.setValue(item.getHoTen(), item.getDoanhSo());
                }

                JFreeChart chart = ChartFactory.createPieChart(
                        "CƠ CẤU DOANH SỐ THEO NHÂN VIÊN",
                        dataset,
                        true, false, false
                );

                PiePlot<String> plot = (PiePlot<String>) chart.getPlot();
                plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                        "{0}: {2}", new DecimalFormat("0"), new DecimalFormat("0%")
                ));
                plot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));

                tab.setData(chart, listData);
            } catch (SQLException ex) {
                ex.printStackTrace();
                tab.showError("Xảy ra lỗi khi tải dữ liệu thống kê: " + ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                tab.showError("Xảy ra lỗi không xác định: " + ex.getMessage());
            }
        }
    }
}
