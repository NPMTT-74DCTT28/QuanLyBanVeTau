package com.group3tt28.quanlybanvetau.controller.core;

import com.group3tt28.quanlybanvetau.dao.ThongKeDAO;
import com.group3tt28.quanlybanvetau.view.core.Dashboard;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Map;

public class DashboardController {

    private final Dashboard dashboard;
    private ThongKeDAO dao;
    private Timer timer;

    public DashboardController(Dashboard dashboard) {
        this.dashboard = dashboard;
        this.dao = ThongKeDAO.getInstance();

        loadData();

        this.timer = new Timer(60000, e -> loadData());
        this.timer.start();

        this.dashboard.setVisible(true);
    }

    private void loadData() {
        try {
            Map<String, String> data = dao.getDashboardData();
            if (data != null) {
                dashboard.setCardData(
                        data.getOrDefault("doanhThu", "0"),
                        data.getOrDefault("soVe", "0"),
                        data.getOrDefault("tauSapChay", "0"));
            }
            DefaultCategoryDataset dataset = dao.getDoanhThuBayNgay();
            if (dataset != null) {
                JFreeChart chart = ChartFactory.createLineChart(
                        "XU HƯỚNG DOANH THU 7 NGÀY GẦN NHẤT".toUpperCase(),
                        "Ngày",
                        "Doanh thu",
                        dataset,
                        PlotOrientation.VERTICAL,
                        false, false, false
                );

                CategoryPlot plot = chart.getCategoryPlot();
                plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_90);
                ((NumberAxis) plot.getRangeAxis()).setAutoRangeIncludesZero(true);

                dashboard.setChart(chart);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            dashboard.showError("Xảy ra lỗi khi tải dữ liệu: " + ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            dashboard.showError("Xảy ra lỗi không xác định: " + e.getMessage());
        }
    }
}
