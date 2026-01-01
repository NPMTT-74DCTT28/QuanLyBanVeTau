package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.ThongKeDAO;
import com.group3tt28.quanlybanvetau.model.dto.DoanhThuTuyen;
import com.group3tt28.quanlybanvetau.view.panel.thongke.TabDoanhThuTuyen;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ThongKeDoanhThuTuyenController {

    private final TabDoanhThuTuyen tab;
    private final ThongKeDAO dao;

    public ThongKeDoanhThuTuyenController(TabDoanhThuTuyen tabDoanhThuTuyen) {
        this.tab = tabDoanhThuTuyen;
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

                if (ChronoUnit.DAYS.between(tuNgay, denNgay) > 365) {
                    tab.showWarning("Khoảng thời gian thống kê quá dài (tối đa 365 ngày).\n" +
                            "Chọn phạm vi nhỏ hơn để biểu đồ hiển thị tốt nhất.");
                    return;
                }

                List<DoanhThuTuyen> list = dao.getDoanhThuTheoTuyen(tuNgay, denNgay);
                if (list.isEmpty()) {
                    tab.showMessage("Không có dữ liệu doanh thu trong khoảng thời gian này!");
                    return;
                }

                DefaultCategoryDataset dataset = new DefaultCategoryDataset();

                for (DoanhThuTuyen item : list) {
                    dataset.addValue(item.getDoanhThu(), "Doanh thu", item.getTenTuyen());
                }

                JFreeChart chart = ChartFactory.createBarChart(
                        "DOANH THU THEO TUYẾN",
                        "Tên tuyến",
                        "Doanh thu",
                        dataset,
                        PlotOrientation.VERTICAL,
                        false, false, false
                );

                tab.setData(chart, list);
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
