package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.ThongKeDAO;
import com.group3tt28.quanlybanvetau.model.dto.KhachHangVIP;
import com.group3tt28.quanlybanvetau.view.panel.thongke.TabKhachHangVIP;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ThongKeKhachHangVIPController {

    private final TabKhachHangVIP tab;
    private final ThongKeDAO dao;

    public ThongKeKhachHangVIPController(TabKhachHangVIP tab) {
        this.tab = tab;
        this.dao = ThongKeDAO.getInstance();

        this.tab.addThongKeListener(new ThongKeListener());

        this.tab.setVisible(true);
    }

    private class ThongKeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int soLuong = tab.getSoLuong();
                if (soLuong <= 0) {
                    tab.showWarning("Số lượng phải từ 1 trở lên.");
                    return;
                }

                List<KhachHangVIP> listData = dao.getKhachHangVIP(soLuong);
                if (listData.isEmpty()) {
                    tab.showMessage("Không tìm thấy dữ liệu khách hàng!");
                    return;
                }

                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                for (KhachHangVIP item : listData) {
                    dataset.addValue(item.getTongTienChiTieu(), "Tổng chi tiêu (VNĐ)", item.getHoTen());
                }

                JFreeChart chart = ChartFactory.createBarChart(
                        "TOP KHÁCH HÀNG CHI TIÊU NHIỀU NHẤT",
                        "Họ tên",
                        "Tổng chi tiêu (VNĐ)",
                        dataset,
                        PlotOrientation.HORIZONTAL,
                        false, false, false
                );

                CategoryPlot plot = chart.getCategoryPlot();
                plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
                ((NumberAxis) plot.getRangeAxis()).setAutoRangeIncludesZero(true);

                tab.setData(chart, listData);
            } catch (SQLException ex) {
                ex.printStackTrace();
                tab.showError("Xảy ra lỗi khi tải dữ liệu khách hàng: " + ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                tab.showError("Xảy ra lỗi không xác định: " + ex.getMessage());
            }
        }
    }
}
