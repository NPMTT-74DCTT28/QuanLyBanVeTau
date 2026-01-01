package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.ThongKeDAO;
import com.group3tt28.quanlybanvetau.model.dto.DoanhThuNgay;
import com.group3tt28.quanlybanvetau.view.panel.thongke.TabDoanhThuNgay;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ThongKeDoanhThuNgayController {

    private final TabDoanhThuNgay tab;
    private final ThongKeDAO dao;

    public ThongKeDoanhThuNgayController(TabDoanhThuNgay tabDoanhThuNgay) {
        this.tab = tabDoanhThuNgay;
        this.dao = ThongKeDAO.getInstance();

        this.tab.addThongKeListener(new ThongKeListener());

        this.tab.setVisible(true);
    }

    private class ThongKeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                LocalDate tuNgay = tab.getTuNgay();
                LocalDate denNgay = tab.getDenNgay();

                if (tuNgay == null || denNgay == null) {
                    tab.showWarning("Vui lòng chọn đủ ngày bắt đầu và ngày kết thúc.");
                    return;
                }

                if (tuNgay.isAfter(denNgay)) {
                    tab.showWarning("Ngày bắt đầu không được lớn hơn ngày kết thúc.");
                    return;
                }

                List<DoanhThuNgay> listDoanhThu = dao.getDoanhThuTheoNgay(tuNgay, denNgay);
                if (listDoanhThu.isEmpty()) {
                    tab.showWarning("Không có dữ liệu doanh thu trong khoảng thời gian này.");
                    return;
                }

                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                for (DoanhThuNgay item : listDoanhThu) {
                    dataset.addValue(item.getDoanhThu(), "Doanh thu", item.getNgay());
                }

                JFreeChart chart = ChartFactory.createLineChart(
                        "BIỂU ĐỒ DOANH THU",
                        "Ngày",
                        "Doanh thu",
                        dataset,
                        PlotOrientation.VERTICAL,
                        false, false, false
                );

                tab.setData(chart, listDoanhThu);
            } catch (SQLException ex) {
                ex.printStackTrace();
                tab.showError("Lỗi khi lấy dữ liệu thống kê: " + ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                tab.showError("Lỗi không xác định: " + ex.getMessage());
            }
        }
    }
}
